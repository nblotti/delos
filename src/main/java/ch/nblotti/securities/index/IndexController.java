package ch.nblotti.securities.index;


import ch.nblotti.securities.index.composition.IndexCompositionDTO;
import ch.nblotti.securities.index.composition.IndexCompositionService;
import ch.nblotti.securities.index.quote.IndexQuoteDTO;
import ch.nblotti.securities.index.quote.IndexQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

  @Autowired
  DateTimeFormatter dateTimeFormatter;

  @Autowired
  IndexCompositionService indexCompositionService;

  @Autowired
  IndexQuoteService indexQuoteService;


  @GetMapping(value = "/composition/")
  public List<IndexCompositionDTO> getIndexDataByDate(@RequestParam LocalDate runDate, @RequestParam String index) {
    return indexCompositionService.getIndexDataByDate(runDate, index);
  }

  public IndexCompositionDTO save(IndexCompositionDTO entity) {
    return indexCompositionService.save(entity);
  }


  @GetMapping(value = "/quote/")
  public List<IndexQuoteDTO> getIndexDataByDate(@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam String index) {
    return indexQuoteService.getIndexDataByDate(fromDate, toDate, index);
  }

  public IndexQuoteDTO save(IndexQuoteDTO entity) {
    return indexQuoteService.save(entity);
  }
}



