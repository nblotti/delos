package ch.nblotti.fx;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/forex")
public class FxQuoteController {

  @Autowired
  FXQuoteService fxQuoteService;

  @GetMapping(value = "/{currencyPair}")
  public Map<LocalDate, FXQuoteDTO> getFXQuotes(@PathVariable String currencyPair) {
    return fxQuoteService.getFXQuotes(currencyPair);
  }

  @GetMapping(value = "/{firstCurrency}/{secondCurrency}/{date}")
  public FXQuoteDTO getFXQuoteForDate(@PathVariable String firstCurrency, @PathVariable String secondCurrency, @PathVariable LocalDate date) {
    return fxQuoteService.getFXQuoteForDate(firstCurrency, secondCurrency, date);
  }


}



