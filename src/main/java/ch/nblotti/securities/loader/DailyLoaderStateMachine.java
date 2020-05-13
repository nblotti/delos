package ch.nblotti.securities.loader;

import ch.nblotti.securities.firm.service.FirmService;
import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.firm.to.FirmEODSharesStatsTO;
import ch.nblotti.securities.firm.to.FirmEODValuationTO;
import ch.nblotti.securities.index.sp500.service.Sp500IndexService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableStateMachine
public class DailyLoaderStateMachine extends EnumStateMachineConfigurerAdapter<LOADER_STATES, LOADER_EVENTS> {


  private static final Logger logger = Logger.getLogger("DailyLoaderStateMachine");

  private final static String EXCHANGE_NYSE = "NYSE";
  private final static String EXCHANGE_NASDAQ = "NASDAQ";

  @Autowired
  private DateTimeFormatter format1;

  public static final String EVENT_MESSAGE_DAY = "firms";

  @Value("${nyse.closed.days}")
  public String nyseClosedDays;

  @Autowired
  Sp500IndexService sp500IndexService;

  @Autowired
  private FirmService firmService;

  @Autowired
  private ModelMapper modelMapper;


  @PostConstruct
  public void initFFirmHistorical() {

//TODO NBL
  }


  @Override
  public void configure(
    StateMachineConfigurationConfigurer
      <LOADER_STATES, LOADER_EVENTS> config) throws Exception {

    config.withConfiguration()
      .autoStartup(true);
  }

  @Override
  public void configure(StateMachineStateConfigurer<LOADER_STATES, LOADER_EVENTS> states) throws Exception {
    states.withStates()
      .initial(LOADER_STATES.READY, initalAction())
      .state(LOADER_STATES.GET_DATES, getDates())
      .state(LOADER_STATES.LOAD_NYSE, loadNYSE())
      .state(LOADER_STATES.LOAD_NASDAQ, loadNASDAQ())
      .state(LOADER_STATES.SAVE_FIRM, saveFirms())
      .end(LOADER_STATES.DONE);


  }


  @Override
  public void configure(StateMachineTransitionConfigurer<LOADER_STATES, LOADER_EVENTS> transitions) throws Exception {
    transitions.withExternal()
      .source(LOADER_STATES.READY).target(LOADER_STATES.GET_DATES).event(LOADER_EVENTS.EVENT_RECEIVED)
      .and()
      .withExternal()
      .source(LOADER_STATES.GET_DATES).target(LOADER_STATES.LOAD_NYSE).event(LOADER_EVENTS.WEEK)
      .and()
      .withExternal()
      .source(LOADER_STATES.GET_DATES).target(LOADER_STATES.DONE).event(LOADER_EVENTS.END_OF_WEEK_OR_DAY_OFF)
      .and()
      .withLocal()
      .source(LOADER_STATES.LOAD_NYSE).target(LOADER_STATES.LOAD_NASDAQ)
      .and()
      .withLocal()
      .source(LOADER_STATES.LOAD_NASDAQ).target(LOADER_STATES.SAVE_FIRM)
      .and()
      .withLocal()
      .source(LOADER_STATES.SAVE_FIRM).target(LOADER_STATES.DONE);
  }


  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> initalAction() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {

        context.getExtendedState().getVariables().put("runDate", LocalDate.now().minusDays(1));
        context.getExtendedState().getVariables().put("runPartial", Boolean.TRUE);

      }
    };
  }


  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> getDates() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {

        Message<LOADER_EVENTS> message;

        LocalDate runDate = (LocalDate) context.getMessageHeader("runDate");
        System.out.println(String.format("%s - Début du traitement", runDate.format(format1)));

        if (runDate == null)
          runDate = (LocalDate) context.getExtendedState().getVariables().get("runDate");
        else
          context.getExtendedState().getVariables().put("runDate", runDate);

        Boolean runPartial = (Boolean) context.getMessageHeader("runPartial");

        if (runPartial == null)
          runPartial = (Boolean) context.getExtendedState().getVariables().get("runPartial");
        else
          context.getExtendedState().getVariables().put("runPartial", runPartial);

        if (runDate.getDayOfMonth() == 1) {
          sp500IndexService.loadSPCompositionAtDate(runDate);
        }


        //on est un jour de week-end (dimanche - lundi) ou hier était férié
        if (runDate.getDayOfWeek() == DayOfWeek.SATURDAY
          || runDate.getDayOfWeek() == DayOfWeek.SUNDAY
          || wasDayBeforeRunDateDayDayOff(runDate)) {
          message = MessageBuilder
            .withPayload(LOADER_EVENTS.END_OF_WEEK_OR_DAY_OFF)
            .build();
        } else
        //on est dimanche ou lundi ou hier était férié
        {
          message = MessageBuilder
            .withPayload(LOADER_EVENTS.WEEK)
            .build();
        }
        context.getStateMachine().sendEvent(message);
      }
    };
  }


  private boolean wasDayBeforeRunDateDayDayOff(LocalDate runDate) {
    return false;
  }


  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> loadNYSE() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> stateContext) {
        loadMarket(DailyLoaderStateMachine.this.EXCHANGE_NYSE, stateContext);
      }
    };

  }

  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> loadNASDAQ() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {
      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> stateContext) {
        loadMarket(DailyLoaderStateMachine.this.EXCHANGE_NASDAQ, stateContext);
      }

    };

  }

  public void loadMarket(final String exchange, StateContext<LOADER_STATES, LOADER_EVENTS> context) {


    LocalDate runDate = (LocalDate) context.getExtendedState().getVariables().get("runDate");
    Collection<FirmEODQuoteTO> firmSaved = (List<FirmEODQuoteTO>) context.getExtendedState().getVariables().get("quotes");
    if (firmSaved == null) {
      firmSaved = new ArrayList<>();
    }


    List<FirmEODQuoteTO> firms = firmService.getExchangeDataForDate(runDate, exchange);
    Iterable<FirmEODQuoteTO> newItemSaved = firmService.saveAllEODMarketQuotes(firms);
    newItemSaved.forEach(firmSaved::add);

    context.getExtendedState()
      .getVariables().put("quotes", firmSaved);
  }


  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> saveFirms() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {


        LocalDate runDate = (LocalDate) context.getExtendedState().getVariables().get("runDate");
        Boolean runPartial = (Boolean) context.getExtendedState().getVariables().get("runPartial");

        List<FirmEODQuoteTO> firms = (List<FirmEODQuoteTO>) context.getExtendedState().getVariables().get("quotes");
        firmService.saveAllEODMarketQuotes(firms);

        if (Boolean.FALSE == runPartial)
          loadDetails(firms, runDate);

        finalAction(runDate);
      }
    };
  }

  public void finalAction(LocalDate runDate) {

    System.out.println(String.format("%s - Fin du traitement", runDate.format(format1)));
  }


  private void loadDetails(List<FirmEODQuoteTO> firms, LocalDate runDate) {


    Collection<FirmEODValuationTO> valuations = new ArrayList<>();
    Collection<FirmEODHighlightsTO> highlights = new ArrayList<>();
    Collection<FirmEODSharesStatsTO> sharesStats = new ArrayList<>();

    for (FirmEODQuoteTO firmEODQuoteTO : firms) {

      if (!sp500IndexService.hasBeenListed(firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode()))
        continue;

      FirmEODValuationTO fVpost = firmService.getValuationByDateAndFirm(runDate, firmEODQuoteTO);
      valuations.add(fVpost);

      FirmEODHighlightsTO fHpost = firmService.getHighlightsByDateAndFirm(runDate, firmEODQuoteTO);
      highlights.add(fHpost);

      FirmEODSharesStatsTO fSpost = firmService.getSharesStatByDateAndFirm(runDate, firmEODQuoteTO);
      sharesStats.add(fSpost);
    }
    firmService.saveAllHighlights(highlights);
    firmService.saveAllValuations(valuations);
    firmService.saveAllSharesStats(sharesStats);
  }


}

