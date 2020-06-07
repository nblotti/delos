package ch.nblotti.securities.index.sp500.controller;


import ch.nblotti.securities.firm.service.FirmService;
import ch.nblotti.securities.index.sp500.respository.ConfigRepository;
import ch.nblotti.securities.index.sp500.service.IndexCompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@RestController
@RequestMapping("/index")
public class IndexCompositionController {

  private static final Logger logger = Logger.getLogger("Sp500IndexController");

  private static final int WORKER_THREAD_POOL = 1;

  @Autowired
  private DateTimeFormatter format1;


  @Autowired
  ConfigRepository configRepository;

  @Autowired
  IndexCompositionService indexCompositionService;

  @Autowired
  private FirmService firmService;


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

  }



  @PostMapping(value = "/ping")
  public long ping() {
    ZoneId zone = ZoneId.of("Europe/Berlin");
    LocalDateTime now = LocalDateTime.now();
    ZoneOffset zoneOffSet = zone.getRules().getOffset(now);
    return LocalDate.now().atStartOfDay(zoneOffSet).toInstant().toEpochMilli();
  }



}
