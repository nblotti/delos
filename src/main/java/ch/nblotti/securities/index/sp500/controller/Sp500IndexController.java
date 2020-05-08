package ch.nblotti.securities.index.sp500.controller;


import ch.nblotti.securities.firm.service.FirmService;
import ch.nblotti.securities.index.sp500.LOADER_EVENTS;
import ch.nblotti.securities.index.sp500.LOADER_STATES;
import ch.nblotti.securities.index.sp500.respository.Sp500IndexSectorIndustryRepository;
import ch.nblotti.securities.index.sp500.service.Sp500IndexService;
import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/index")
public class Sp500IndexController {


  @Autowired
  Sp500IndexService sp500IndexService;
  @Autowired
  Sp500IndexSectorIndustryRepository sp500IndexSectorIndustryRepository;

  @Autowired
  private FirmService firmService;


  @Autowired
  private StateMachine<LOADER_STATES, LOADER_EVENTS> sp500LoaderStateMachine;

  @GetMapping(value = "/init")
  public void init() {
    sp500LoaderStateMachine.sendEvent(LOADER_EVENTS.EVENT_RECEIVED);
    int a = 2;
  }

  @GetMapping(value = "/load")
  public void load(@RequestParam(name = "startyear", required = true) Integer startYear, @RequestParam(name = "endyear", required = false) Integer endYear, @RequestParam(name = "startmonth", required = true) Integer startMonth, @RequestParam(name = "endmonth", required = false) Integer endMonth) {

    if (endYear == null || endYear == 0)
      endYear = startYear;

    if (startYear < 0 || startYear > endYear)
      throw new IllegalArgumentException("Start year cannot be bigger than end year");


    if (endMonth == null || endMonth == 0)
      endMonth = startMonth;

    if (startMonth > 12 || endMonth > 12 || startMonth > endMonth)
      throw new IllegalArgumentException("End month or start month cannot be bigger than 12. start month cannot be bigger than end month");




    int december = 12;

    for (int year = startYear; year <= endYear; year++) {
      if (year == endYear)
        december = endMonth;
      for (int month = startMonth; month <= december; month++) {
        LocalDate localDate = localDate(year, month);


        //TODO NBL
      }

    }
  }


  private LocalDate localDate(int year, int month) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

    return convertToLocalDateViaInstant(cal.getTime());

  }


  private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
  }


  @GetMapping(value = "/sp500/{sector}/{industry}/")
  public Iterable<Sp500IndexSectorIndustryTO> sp500MarketCapBySectorIndustryDate(@NotNull @PathVariable String sector, @NotNull @PathVariable String industry, @NotNull @RequestParam(name = "startyear", required = true) Integer startYear, @RequestParam(name = "startmonth", required = true) Integer startMonth) {

    DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    int month = 1;
    if (startMonth != null)
      month = startMonth;

    LocalDate localDate = localDate(startYear, month);

    return sp500IndexSectorIndustryRepository.findAllBySectorAndIndustryAndDateAfter(sector, industry, localDate);
  }


}
