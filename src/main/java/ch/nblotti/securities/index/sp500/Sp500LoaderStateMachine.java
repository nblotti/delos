package ch.nblotti.securities.index.sp500;

import ch.nblotti.securities.firm.FirmPO;
import ch.nblotti.securities.firm.FirmService;
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

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;

@Configuration
@EnableStateMachine
public class Sp500LoaderStateMachine extends EnumStateMachineConfigurerAdapter<LOADER_STATES, LOADER_EVENTS> {


  @Value("${nyse.closed.days}")
  public String nyseClosedDays;

  @Autowired
  Sp500IndexService sp500IndexService;

  @Autowired
  private FirmService firmService;

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
      .initial(LOADER_STATES.READY)
      .state(LOADER_STATES.CHECK_END_OF_MONTH, isEndofMonth())
      .state(LOADER_STATES.DETERMINING_DATE, getDayOfWeek())
      .state(LOADER_STATES.FIRM, updateFirm())
      .state(LOADER_STATES.MARKET_CAP, getMarketCap())
      .state(LOADER_STATES.PRICE, getPrice())
      .end(LOADER_STATES.DONE);


  }

  @Override
  public void configure(StateMachineTransitionConfigurer<LOADER_STATES, LOADER_EVENTS> transitions) throws Exception {
    transitions.withExternal()
      .source(LOADER_STATES.READY).target(LOADER_STATES.CHECK_END_OF_MONTH).event(LOADER_EVENTS.EVENT_RECEIVED)
      .and()
      .withExternal()
      .source(LOADER_STATES.CHECK_END_OF_MONTH).target(LOADER_STATES.FIRM).event(LOADER_EVENTS.END_OF_MONTH)
      .and()
      .withExternal()
      .source(LOADER_STATES.CHECK_END_OF_MONTH).target(LOADER_STATES.DETERMINING_DATE).event(LOADER_EVENTS.NOT_END_OF_MONTH)
      .and()
      .withLocal()
      .source(LOADER_STATES.FIRM).target(LOADER_STATES.DETERMINING_DATE)
      .and()
      .withExternal()
      .source(LOADER_STATES.DETERMINING_DATE).target(LOADER_STATES.DONE).event(LOADER_EVENTS.END_OF_WEEK_DAY_OFF)
      .and()
      .withExternal()
      .source(LOADER_STATES.DETERMINING_DATE).target(LOADER_STATES.MARKET_CAP).event(LOADER_EVENTS.DAY_OF_WEEK)
      .and()
      .withLocal()
      .source(LOADER_STATES.MARKET_CAP).target(LOADER_STATES.PRICE)
      .and()
      .withLocal()
      .source(LOADER_STATES.PRICE).target(LOADER_STATES.DONE);
  }

  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> getDayOfWeek() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {

        // context.getStateMachine();
        int a = 2;


        if (wasYesterdayEndOfWeekOrDayOff())
          context.getStateMachine().sendEvent(LOADER_EVENTS.END_OF_WEEK_DAY_OFF);
        else context.getStateMachine().sendEvent(LOADER_EVENTS.DAY_OF_WEEK);
      }
    };
  }


  private boolean wasYesterdayEndOfWeekOrDayOff() {

    if (true) return false;
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
    cal.clear(Calendar.MINUTE);
    cal.clear(Calendar.SECOND);
    cal.clear(Calendar.MILLISECOND);

    //dimanche (1) & lundi (2)
    if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 2)
      return true;

    return false;
  }

  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> isEndofMonth() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // if (cal.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)) {
        sp500IndexService.deleteByDate(LocalDate.now());
        Collection<FirmPO> firms = sp500IndexService.getSPCurrentComposition();
        sp500IndexService.saveIndexCompositionAtDate(LocalDate.now(), firms);


        Message<LOADER_EVENTS> message = MessageBuilder
          .withPayload(LOADER_EVENTS.END_OF_MONTH)
          .setHeader("firms", firms)
          .build();

        context.getStateMachine().sendEvent(message);

        // }

      }
    };
  }

  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> updateFirm() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {

        Collection<FirmPO> firms = (Collection<FirmPO>) context.getMessage().getHeaders().get("firms");
        firmService.saveAllIfNotExist(firms);

      }
    };
  }

  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> getMarketCap() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {
        int a = 2;
      }
    };
  }

  @Bean
  public Action<LOADER_STATES, LOADER_EVENTS> getPrice() {
    return new Action<LOADER_STATES, LOADER_EVENTS>() {

      @Override
      public void execute(StateContext<LOADER_STATES, LOADER_EVENTS> context) {
        int a = 2;
      }
    };
  }
}
