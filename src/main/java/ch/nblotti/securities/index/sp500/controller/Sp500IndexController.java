package ch.nblotti.securities.index.sp500.controller;


import ch.nblotti.securities.firm.service.FirmService;
import ch.nblotti.securities.index.sp500.respository.Sp500IndexSectorIndustryRepository;
import ch.nblotti.securities.index.sp500.service.Sp500IndexService;
import ch.nblotti.securities.loader.LOADER_EVENTS;
import ch.nblotti.securities.loader.LOADER_STATES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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

    int localStartDay;


    if (startDay == null || startDay <= 0)
      localStartDay = 1;
    else
      localStartDay = startDay;

    int localEndDay = LocalDate.of(endYear, endMonth, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
    if (endDay <= localEndDay)
      localEndDay = endDay;


    Message<LOADER_EVENTS> message;


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
          // startLoadingProcess(runDate, message);
        }


      }
    }
  }

  @PostMapping(value = "/load/{year}/{month}/{day}")
  public void load(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day) {

    LocalDate localDate = LocalDate.parse(String.format("%s-%s-%s", year, month, day), format1);

    Message<LOADER_EVENTS> message = MessageBuilder
      .withPayload(LOADER_EVENTS.EVENT_RECEIVED)
      .setHeader("runDate", localDate)
      .build();


    startLoadingProcess(localDate, message);

  }

  private void startLoadingProcess(LocalDate localDate, Message<LOADER_EVENTS> message) {
    sp500LoaderStateMachine.start();
    boolean result = sp500LoaderStateMachine.sendEvent(message);
    while (sp500LoaderStateMachine.getState().getId() != LOADER_STATES.DONE) {
      try {
        Thread.sleep(60000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(String.format("%s - %s", localDate, result));
      sp500LoaderStateMachine.stop();
    }
  }

  @PostMapping(value = "/ping")
  public long ping() {
    ZoneId zone = ZoneId.of("Europe/Berlin");
    LocalDateTime now = LocalDateTime.now();
    ZoneOffset zoneOffSet = zone.getRules().getOffset(now);
    return LocalDate.now().atStartOfDay(zoneOffSet).toInstant().toEpochMilli();
  }

}
