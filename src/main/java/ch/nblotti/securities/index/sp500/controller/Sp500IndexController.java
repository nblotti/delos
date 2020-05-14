package ch.nblotti.securities.index.sp500.controller;


import ch.nblotti.securities.firm.service.FirmService;
import ch.nblotti.securities.index.sp500.respository.Sp500IndexSectorIndustryRepository;
import ch.nblotti.securities.index.sp500.service.Sp500IndexService;
import ch.nblotti.securities.loader.LOADER_EVENTS;
import ch.nblotti.securities.loader.LOADER_STATES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/index")
public class Sp500IndexController {

  private static final Logger logger = Logger.getLogger("Sp500IndexController");


  @Autowired
  private DateTimeFormatter format1;

  @Autowired
  Sp500IndexService sp500IndexService;
  @Autowired
  Sp500IndexSectorIndustryRepository sp500IndexSectorIndustryRepository;

  @Autowired
  private FirmService firmService;

  @Autowired

  private StateMachine<LOADER_STATES, LOADER_EVENTS> sp500LoaderStateMachine;


  @PostMapping(value = "/load")
  public void load(@RequestParam(name = "startyear", required = true) Integer startYear,
                   @RequestParam(name = "endyear", required = false) Integer endYear,
                   @RequestParam(name = "startmonth", required = true) Integer startMonth,
                   @RequestParam(name = "endmonth", required = false) Integer endMonth,
                   @RequestParam(name = "startday", required = false) Integer startDay,
                   @RequestParam(name = "endday", required = false) Integer endDay
  ) {

    if (endYear == null || endYear == 0)
      endYear = startYear;

    if (startYear < 2000 || startYear > endYear)
      throw new IllegalArgumentException("Start year cannot be bigger than end year");


    if (endMonth == null || endMonth == 0)
      endMonth = startMonth;

    if (endYear == null || endYear == 0)
      endYear = startYear;

    if (startMonth > 12 || endMonth > 12) {
      throw new IllegalArgumentException("End month or start month cannot be bigger than 12. start month cannot be bigger than end month");
    }


    ExecutorService executor = Executors.newFixedThreadPool(10);

    LoaderThread loaderThread = new LoaderThread(sp500LoaderStateMachine, startYear, startMonth, startDay, endYear, endMonth, endDay);

    executor.submit(loaderThread);
  }

/*
  @PostMapping(value = "/load/{year}/{month}/{day}")
  public void load(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day) {

    LocalDate localDate = LocalDate.parse(String.format("%s-%s-%s", year, month, day), format1);

    Message<LOADER_EVENTS> message = MessageBuilder
      .withPayload(LOADER_EVENTS.EVENT_RECEIVED)
      .setHeader("runDate", localDate)
      .build();


    startLoadingProcess(localDate, message);

  }
*/

  @PostMapping(value = "/ping")
  public long ping() {
    ZoneId zone = ZoneId.of("Europe/Berlin");
    LocalDateTime now = LocalDateTime.now();
    ZoneOffset zoneOffSet = zone.getRules().getOffset(now);
    return LocalDate.now().atStartOfDay(zoneOffSet).toInstant().toEpochMilli();
  }


  class LoaderThread implements Runnable {

    private final Logger logger = Sp500IndexController.this.logger;

    private final StateMachine<LOADER_STATES, LOADER_EVENTS> sp500LoaderStateMachine;
    private final Integer startYear;
    private final Integer startMonth;
    private final Integer startDay;
    private final Integer endYear;
    private final Integer endMonth;
    private final Integer endDay;

    public LoaderThread(StateMachine<LOADER_STATES, LOADER_EVENTS> sp500LoaderStateMachine, Integer startYear, Integer startMonth, Integer startDay, Integer endYear, Integer endMonth, Integer endDay) {
      this.sp500LoaderStateMachine = sp500LoaderStateMachine;
      this.startYear = startYear;
      this.startMonth = startMonth;
      this.startDay = startDay;
      this.endYear = endYear;
      this.endMonth = endMonth;
      this.endDay = endDay;
    }

    private void startLoadingProcess(LocalDate localDate, Message<LOADER_EVENTS> message) {

      long start = System.nanoTime();
      sp500LoaderStateMachine.start();
      boolean result = sp500LoaderStateMachine.sendEvent(message);
      while (sp500LoaderStateMachine.getState().getId() != LOADER_STATES.DONE) {
        try {
          long temp = System.nanoTime();
          long secs = TimeUnit.SECONDS.convert(temp - start, TimeUnit.NANOSECONDS);
          logger.log(Level.INFO, String.format("%s - still running - temps : %s secondes", localDate, secs));

          Thread.sleep(15000);

        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      sp500LoaderStateMachine.stop();
      long end = System.nanoTime();
      long secs = TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS);

      logger.log(Level.INFO, String.format("%s - %s - temps : %s secondes", localDate, result, secs));

    }

    @Override
    public void run() {


      Message<LOADER_EVENTS> message;

      int localStartDay;
      if (startDay == null || startDay <= 0)
        localStartDay = 1;
      else
        localStartDay = startDay;

      int localEndDay = LocalDate.of(endYear, endMonth, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
      if (endDay <= localEndDay)
        localEndDay = endDay;


      for (int year = startYear; year <= endYear; year++) {

        int loopstartMonth = 1;
        int loopLastMonth = 12;

        if (year == endYear)
          loopLastMonth = endMonth;

        if (year == startYear)
          loopstartMonth = startMonth;

        for (int month = loopstartMonth; month <= loopLastMonth; month++) {
          LocalDate localDate = LocalDate.of(year, month, 1);
          localDate = localDate.withDayOfMonth(localDate.lengthOfMonth());

          int loopLastDay = 1;
          int loopStartDay = 1;

          loopLastDay = localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

          if (year >= endYear && month >= endMonth) {
            loopLastDay = localEndDay;
          }

          if (year == startYear && month == startMonth) {
            loopStartDay = localStartDay;
          }
          for (int day = loopStartDay; day <= loopLastDay; day++) {
            LocalDate runDate = localDate.withDayOfMonth(day);

            if (runDate.isAfter(LocalDate.now().minusDays(1)))
              return;

            message = MessageBuilder
              .withPayload(LOADER_EVENTS.EVENT_RECEIVED)
              .setHeader("runDate", runDate)
              .build();

            logger.log(Level.INFO, String.format("%s-%s-%s", year, month, day));
            startLoadingProcess(runDate, message);
          }


        }
      }
    }


  }


}
