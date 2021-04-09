package ch.nblotti.securities.index;


import ch.nblotti.securities.firm.quote.FirmPeriodicQuoteDTO;
import ch.nblotti.securities.firm.quote.FirmQuoteService;
import ch.nblotti.securities.index.composition.IndexCompositionDTO;
import ch.nblotti.securities.index.composition.IndexCompositionService;
import ch.nblotti.securities.index.quote.IndexQuoteDTO;
import ch.nblotti.securities.index.quote.IndexQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
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


  @GetMapping(value = "/")
  public Iterable<FirmPeriodicQuoteDTO> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(@RequestParam("period") String period, @RequestParam("from") LocalDate startDate) {

    ChronoUnit type;
    switch (period) {
      case "w":
        type = ChronoUnit.WEEKS;
        break;
      case "m":
        type = ChronoUnit.MONTHS;
        break;
      default:
        return Collections.emptyList();
    }
    return indexQuoteService.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(type, startDate);
  }

  @Transactional
  @PostMapping(value = "/refresh")
  public void refreshMaterializedView() {
    indexQuoteService.refreshMaterializedView();
  }


  @PostMapping(value = "/composition/")
  public Iterable<IndexCompositionDTO> saveIndexComposition(@RequestBody List<IndexCompositionDTO> indexCompositionDTOs) {
    return indexCompositionService.saveIndexComposition(indexCompositionDTOs);
  }

  @DeleteMapping(value = "/composition/")
  public void deleteAllComposition() {
    indexCompositionService.deleteAll();
  }


  @PostMapping(value = "/quote")
  public IndexQuoteDTO saveEODMarketQuote(@RequestBody IndexQuoteDTO indexQuoteDTO) {
    return indexQuoteService.save(indexQuoteDTO);
  }

  @PostMapping(value = "/quotes")
  public Collection<IndexQuoteDTO> saveAllEODMarketQuote(@RequestBody Collection<IndexQuoteDTO> indexQuoteDTOs) {
    return indexQuoteService.saveAll(indexQuoteDTOs);
  }

}



