package ch.nblotti.securities.firm.highlights;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
class EODFirmHighlightsRepository {


  private static final Logger logger = Logger.getLogger("FirmEODRepository");

  public static final String EXCHANGE = "exchange";
  public static final String EXCHANGE_JSON = "exchangelJson";
  public static final String FIRMS_FINANCIALS = "firms";
  public static final String FIRMS_FINANCIALS_JSON = "firmsFinancialJson";

  @Autowired
  Cache cacheOne;

  @Value("${firm.marketCap.bulk.url}")
  private String marketCap;

  @Value("${index.firm.api.url}")
  public String firmUrl;


  public String highlightStr = "$.Highlights";


  @Autowired
  protected RestTemplate restTemplate;


  @Value("${spring.application.eod.api.key}")
  protected String apiKey;


  public Optional<EODFirmHighlightsDTO> getHighlightsByDateAndFirm(LocalDate runDate, String exchange, String symbol) {


    String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
    final ResponseEntity<String> response = getStringResponseEntity(finalUrl);
    try {
      DocumentContext jsonContext = JsonPath.parse(response.getBody());
      EODFirmHighlightsDTO EODFirmHighlightsDTO = jsonContext.read(highlightStr, EODFirmHighlightsDTO.class);

      return Optional.of(EODFirmHighlightsDTO);
    } catch (Exception ex) {
      logger.log(Level.INFO, String.format("Error, mapping highlight for symbol %s \r\n%s", symbol, ex.getMessage()));
      return Optional.empty();
    }
  }


  protected ResponseEntity<String> getStringResponseEntity(String finalUrl) {
    if (cacheOne.get(finalUrl.hashCode()) == null) {
      boolean networkErrorHandling = false;
      while (!networkErrorHandling) {
        try {
          ResponseEntity<String> entity = restTemplate.getForEntity(finalUrl, String.class);
          cacheOne.put(finalUrl.hashCode(), entity);
          return entity;
        } catch (Exception ex) {
          logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
        }
      }
      throw new IllegalStateException();
    }

    return (ResponseEntity<String>) cacheOne.get(finalUrl.hashCode()).get();
  }

}
