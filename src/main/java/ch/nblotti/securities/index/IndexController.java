package ch.nblotti.securities.index;


import ch.nblotti.securities.firm.quote.FirmQuoteDTO;
import ch.nblotti.securities.index.composition.IndexCompositionDTO;
import ch.nblotti.securities.index.composition.IndexCompositionService;
import ch.nblotti.securities.index.quote.IndexQuoteDTO;
import ch.nblotti.securities.index.quote.IndexQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
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


  @PostMapping(value = "/composition/")
  public Iterable<IndexCompositionDTO> saveIndexComposition(@RequestParam Collection<IndexCompositionDTO> indexCompositionDTOs) {
    return indexCompositionService.saveIndexComposition(indexCompositionDTOs);
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



