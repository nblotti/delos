package ch.nblotti.securities.firm;


import ch.nblotti.securities.firm.highlights.FirmHighlightsDTO;
import ch.nblotti.securities.firm.highlights.FirmHighlightsService;
import ch.nblotti.securities.firm.infos.FirmInfoDTO;
import ch.nblotti.securities.firm.infos.FirmInfoService;
import ch.nblotti.securities.firm.quote.FirmPeriodicQuoteDTO;
import ch.nblotti.securities.firm.quote.FirmQuoteDTO;
import ch.nblotti.securities.firm.quote.FirmQuoteService;
import ch.nblotti.securities.firm.sharestats.FirmShareStatsDTO;
import ch.nblotti.securities.firm.sharestats.FirmSharesStatsService;
import ch.nblotti.securities.firm.split.FirmSplitDTO;
import ch.nblotti.securities.firm.split.FirmSplitService;
import ch.nblotti.securities.firm.valuation.FirmValuationDTO;
import ch.nblotti.securities.firm.valuation.FirmValuationService;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/quotes")
@Transactional
public class QuoteController {

  @Autowired
  DateTimeFormatter dateTimeFormatter;

  @Autowired
  FirmQuoteService firmQuoteService;


  @GetMapping(value = "/{code}")
  public Iterable<FirmPeriodicQuoteDTO> findAllByCodeAndTypeAndStartDateAndENdDate(@PathVariable("code") String code, @RequestParam("period") String period, @RequestParam("from") LocalDate startDate, @RequestParam("to") LocalDate endDate) {

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
    return firmQuoteService.findAllByCodeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(code, type, startDate, endDate);
  }


}



