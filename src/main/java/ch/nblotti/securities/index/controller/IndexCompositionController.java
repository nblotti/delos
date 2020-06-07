package ch.nblotti.securities.index.controller;


import ch.nblotti.securities.index.service.IndexCompositionService;
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
  IndexCompositionService indexCompositionService;


  @PostMapping(value = "/load")
  public void load(@RequestParam(name = "date", required = true) LocalDate localDate, @RequestParam(name = "index", required = true) String index) {


    indexCompositionService.loadSPCompositionAtDate(localDate, index);

  }


  @PostMapping(value = "/ping")
  public long ping() {
    ZoneId zone = ZoneId.of("Europe/Berlin");
    LocalDateTime now = LocalDateTime.now();
    ZoneOffset zoneOffSet = zone.getRules().getOffset(now);
    return LocalDate.now().atStartOfDay(zoneOffSet).toInstant().toEpochMilli();
  }


}
