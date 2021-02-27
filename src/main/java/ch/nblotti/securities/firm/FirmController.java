package ch.nblotti.securities.firm;


import ch.nblotti.securities.firm.highlights.FirmHighlightsDTO;
import ch.nblotti.securities.firm.highlights.FirmHighlightsService;
import ch.nblotti.securities.firm.infos.FirmInfoDTO;
import ch.nblotti.securities.firm.infos.FirmInfoService;
import ch.nblotti.securities.firm.quote.FirmQuoteDTO;
import ch.nblotti.securities.firm.quote.FirmQuoteService;
import ch.nblotti.securities.firm.sharestats.FirmShareStatsDTO;
import ch.nblotti.securities.firm.sharestats.FirmSharesStatsService;
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
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/firm")
public class FirmController {

  @Autowired
  DateTimeFormatter dateTimeFormatter;

  @Autowired
  FirmQuoteService firmQuoteService;

  @Autowired
  FirmInfoService firmInfoService;

  @Autowired
  FirmHighlightsService firmHighlightsService;

  @Autowired
  FirmSharesStatsService firmSharesStatsService;

  @Autowired
  FirmValuationService firmValuationService;

  @Autowired
  RestTemplate restTemplate;

  @Value("${ft.api.key}")
  private String ftApiKey;

  @Value("${solr.server.uri}")
  private String searchServerHost;
  @Value("${ft.server.articles.request.body}")
  String requestBody;

  @Value("${ft.server.articles.api}")
  private String ftServerArticlesApi;

  public String highlightStr = "$.response.docs";

  @PostMapping(value = "/search/")
  public List<FirmSearchDto> mirrorRest(@RequestBody String body, @RequestHeader MultiValueMap<String, String> headers) throws URISyntaxException {

    String url = String.format("%s", searchServerHost);

    ResponseEntity<String> responseEntity =
      restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(body, headers), String.class);

    Configuration conf = Configuration
      .builder()
      .mappingProvider(new JacksonMappingProvider())
      .jsonProvider(new JacksonJsonProvider())
      .build();

    TypeRef<List<FirmSearchDto>> type = new TypeRef<List<FirmSearchDto>>() {
    };

    List<FirmSearchDto> firms = JsonPath
      .using(conf)
      .parse(responseEntity.getBody())
      .read(highlightStr, type);

    return firms;


  }

  @PostMapping(value = "/ftarticles")
  public ResponseEntity<String> ftArticles(HttpServletResponse response, @RequestParam String firmName, @RequestParam LocalDate date) {

    String requestBodyJson = String.format(requestBody, firmName, dateTimeFormatter.format(date));
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-Api-Key", ftApiKey);
    HttpEntity<String> request = new HttpEntity<String>(requestBodyJson, headers);

    return
      restTemplate.exchange(ftServerArticlesApi, HttpMethod.POST, request, String.class);


  }


  @PostMapping(value = "/exchange")
  public FirmQuoteDTO saveEODMarketQuote(@RequestBody FirmQuoteDTO firmsTO) {
    return firmQuoteService.save(firmsTO);
  }

  @DeleteMapping(value = "/exchange")
  public void deleteMarketQuoteByDate(@RequestParam LocalDate localDate) {
    firmQuoteService.deleteByDate(localDate);
  }

  @PostMapping(value = "/infos")
  public FirmInfoDTO saveFirmInfo(@RequestBody FirmInfoDTO firmInfoDTO) {

    return firmInfoService.save(firmInfoDTO);
  }

  @DeleteMapping(value = "/infos")
  public void deleteFirmInfoByDate(@RequestParam LocalDate localDate) {
    firmInfoService.deleteByDate(localDate);
  }

  @PostMapping(value = "/valuations")
  public FirmValuationDTO saveFirmValuation(@RequestBody FirmValuationDTO firmValuationDTO) {

    return firmValuationService.save(firmValuationDTO);

  }

  @DeleteMapping(value = "/valuations")
  public void deleteFirmValuationByDate(@RequestParam LocalDate localDate) {
    firmValuationService.deleteByDate(localDate);
  }


  @PostMapping(value = "/highlights")
  public FirmHighlightsDTO saveFirmHighlight(@RequestBody FirmHighlightsDTO firmHighlightsDTO) {
    return firmHighlightsService.save(firmHighlightsDTO);

  }

  @DeleteMapping(value = "/highlights")
  public void deleteFirmHighlightByDate(@RequestParam LocalDate localDate) {
    firmHighlightsService.deleteByDate(localDate);
  }

  @PostMapping(value = "/sharestats")
  public FirmShareStatsDTO saveFirmShareStats(@RequestBody FirmShareStatsDTO firmShareStatsDTO) {

    return firmSharesStatsService.save(firmShareStatsDTO);

  }

  @DeleteMapping(value = "/sharestats")
  public void deleteFirmShareStatsByDate(@RequestParam LocalDate localDate) {
    firmSharesStatsService.deleteByDate(localDate);
  }


}



