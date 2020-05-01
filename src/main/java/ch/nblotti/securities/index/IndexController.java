package ch.nblotti.securities.index;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/index")
public class IndexController {


  @Autowired
  Sp500IndexService sp500IndexService;
  @Autowired
  Sp500IndexSectorIndustryRepository sp500IndexSectorIndustryRepository;


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

    sp500IndexService.load(startYear, endYear, startMonth, endMonth);


  }

  @GetMapping(value = "/delete")
  public void load(@RequestParam(name = "startyear", required = true) Integer startYear, @RequestParam(name = "startmonth", required = true) Integer startMonth) {

    sp500IndexService.deleteIndexCompositionAtDate(startYear, startMonth);
  }

  @GetMapping(value = "/sp500/{sector}/{industry}/")
  public Iterable<Sp500IndexSectorIndustryPO> sp500MarketCapBySectorIndustryDate(@NotNull @PathVariable String sector, @NotNull @PathVariable String industry, @NotNull @RequestParam(name = "startyear", required = true) Integer startYear, @RequestParam(name = "startmonth", required = true) Integer startMonth) {

    DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    int month = 1;
    if (startMonth != null)
      month = startMonth;

    LocalDate localDate = localDate(startYear, month);

    return sp500IndexSectorIndustryRepository.findAllBySectorAndIndustryAndDateAfter(sector, industry, localDate);
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

}
