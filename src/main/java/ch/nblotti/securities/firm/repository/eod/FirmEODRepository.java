package ch.nblotti.securities.firm.repository.eod;

import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.firm.to.FirmEODShareStatsTO;
import ch.nblotti.securities.firm.to.FirmEODValuationTO;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class FirmEODRepository {


  private static final Logger logger = Logger.getLogger("FirmEODRepository");

  public static final String EXCHANGE = "exchange";
  public static final String EXCHANGE_JSON = "exchangelJson";
  public static final String FIRMS_FINANCIALS = "firms";
  public static final String FIRMS_FINANCIALS_JSON = "firmsFinancialJson";


  @Autowired
  private DateTimeFormatter format1;


  @Value("${firm.marketCap.bulk.url}")
  private String marketCap;


  @Value("${index.firm.api.url}")
  public String firmUrl;

  @Value("${spring.application.eod.api.key}")
  public String apiKey;

  public String highlightStr = "$.Highlights";
  public String valuationStr = "$.Valuation";
  public String sharesStatStr = "$.SharesStats";
  public String sharesHistoryStr = "$.[*]";

  @Autowired
  private ModelMapper modelMapper;


  @Autowired
  private CacheManager cacheManager;


  public FirmEODValuationTO getValuationByDateAndFirm(LocalDate runDate, String exchange, String symbol) {

    String key = String.format("%s-%s-%s", FIRMS_FINANCIALS, runDate.format(format1), symbol);

    logger.log(Level.INFO, String.format("%s - Loading valuation details for %s %s ",runDate.format(format1),exchange,symbol));
    if (cacheManager.getCache(FIRMS_FINANCIALS).get(key) == null) {

      boolean networkErrorHandling = false;
      while (!networkErrorHandling) {
        try {
          String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
          final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);
          DocumentContext content = JsonPath.parse(response.getBody());
          cacheManager.getCache(FIRMS_FINANCIALS).put(key, content);
          networkErrorHandling = true;
        } catch (Exception ex) {
          logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
        }
      }
    }
    ValuationDTO valuationDTO = ((DocumentContext) cacheManager.getCache(FIRMS_FINANCIALS).get(key).get()).read(valuationStr, ValuationDTO.class);

    FirmEODValuationTO fVpost = modelMapper.map(valuationDTO, FirmEODValuationTO.class);
    fVpost.setExchange(exchange);
    fVpost.setDate(runDate);
    fVpost.setCode(symbol);

    return fVpost;

  }

  public FirmEODHighlightsTO getHighlightsByDateAndFirm(LocalDate runDate, String exchange, String symbol) {

    logger.log(Level.INFO, String.format("%s - Loading highlights details for %s %s ",runDate.format(format1),exchange,symbol));

    String key = String.format("%s-%s-%s", FIRMS_FINANCIALS, runDate.format(format1), symbol);

    DocumentContext jsonContext = (DocumentContext) cacheManager.getCache(FIRMS_FINANCIALS).get(key).get();

    if (jsonContext == null) {
      String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
      boolean networkErrorHandling = false;
      while (!networkErrorHandling) {
        try {
          final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);
          jsonContext = JsonPath.parse(response.getBody());
          cacheManager.getCache(FIRMS_FINANCIALS).put(key, jsonContext);
          networkErrorHandling = true;
        } catch (Exception ex) {
          logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
        }
      }
    }
    FirmHighlightsDTO firmHighlightsDTO = jsonContext.read(highlightStr, FirmHighlightsDTO.class);
    FirmEODHighlightsTO fHpost = modelMapper.map(firmHighlightsDTO, FirmEODHighlightsTO.class);
    fHpost.setExchange(exchange);
    fHpost.setDate(runDate);
    fHpost.setCode(symbol);

    return fHpost;
  }

  public FirmEODShareStatsTO getSharesStatByDateAndExchangeAndFirm(LocalDate runDate, String exchange, String symbol) {

    logger.log(Level.INFO, String.format("%s - Loading ShareStats details for %s %s ",runDate.format(format1),exchange,symbol));

    String key = String.format("%s-%s-%s", FIRMS_FINANCIALS, runDate.format(format1), symbol);

    DocumentContext jsonContext = (DocumentContext) cacheManager.getCache(FIRMS_FINANCIALS).get(key).get();
    if (jsonContext == null) {
      boolean networkErrorHandling = false;
      while (!networkErrorHandling) {
        try {
          String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
          final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);
          jsonContext = JsonPath.parse(response.getBody());
          cacheManager.getCache(FIRMS_FINANCIALS).put(key, jsonContext);
          networkErrorHandling = true;
        } catch (Exception ex) {
          logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
        }
      }
    }
    SharesStatsDTO sharesStatsDTO = jsonContext.read(sharesStatStr, SharesStatsDTO.class);

    FirmEODShareStatsTO fSpost = modelMapper.map(sharesStatsDTO, FirmEODShareStatsTO.class);
    fSpost.setExchange(exchange);
    fSpost.setDate(runDate);
    fSpost.setCode(symbol);

    return fSpost;
  }


  //@Scheduled(cron = "0 0 1 * * MON")
  @Scheduled(fixedRate = 90000)
  public void clearCache() {
    cacheManager.getCache(FIRMS_FINANCIALS).clear();

  }

  @Autowired
  private RestTemplate restTemplate;


  public List<FirmEODQuoteTO> getExchangeDataByDate(LocalDate runDate, String exchange) {

    DocumentContext jsonContext = null;
    boolean networkErrorHandling = false;
    while (!networkErrorHandling) {
      try {
        String finalUrl = String.format(marketCap, exchange, apiKey, runDate.format(format1));
        final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);
        jsonContext = JsonPath.parse(response.getBody());
        networkErrorHandling = true;
      } catch (Exception ex) {
        logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
      }
    }
    List<FirmDTO> firms = Arrays.asList(jsonContext.read(sharesHistoryStr, FirmDTO[].class));

    List<FirmEODQuoteTO> firmsTOs = firms.stream().map(x -> modelMapper.map(x, FirmEODQuoteTO.class)).collect(Collectors.toList());

    return firmsTOs;
  }

  @PostConstruct
  public void initFirmHighlightsMapper() {

    Converter<FirmHighlightsDTO, FirmEODHighlightsTO> toUppercase = new AbstractConverter<FirmHighlightsDTO, FirmEODHighlightsTO>() {

      @Override
      protected FirmEODHighlightsTO convert(FirmHighlightsDTO firmHighlightsDTO) {
        FirmEODHighlightsTO firmEODHighlightsTO = new FirmEODHighlightsTO();
        firmEODHighlightsTO.setMarketCapitalization(firmHighlightsDTO.getMarketCapitalization());
        firmEODHighlightsTO.setMarketCapitalizationMln(firmHighlightsDTO.getMarketCapitalizationMln());
        firmEODHighlightsTO.seteBITDA(firmHighlightsDTO.getEBITDA());
        firmEODHighlightsTO.setpERatio(firmHighlightsDTO.getPERatio());
        firmEODHighlightsTO.setpEGRatio(firmHighlightsDTO.getPEGRatio());
        firmEODHighlightsTO.setWallStreetTargetPrice(firmHighlightsDTO.getWallStreetTargetPrice());
        firmEODHighlightsTO.setBookValue(firmHighlightsDTO.getBookValue());
        firmEODHighlightsTO.setDividendShare(firmHighlightsDTO.getDividendShare());
        firmEODHighlightsTO.setDividendYield(firmHighlightsDTO.getDividendYield());
        firmEODHighlightsTO.setEarningsShare(firmHighlightsDTO.getEarningsShare());
        firmEODHighlightsTO.setePSEstimateCurrentYear(firmHighlightsDTO.getEPSEstimateCurrentYear());
        firmEODHighlightsTO.setePSEstimateNextYear(firmHighlightsDTO.getEPSEstimateNextYear());
        firmEODHighlightsTO.setePSEstimateNextQuarter(firmHighlightsDTO.getEPSEstimateNextQuarter());
        firmEODHighlightsTO.setePSEstimateCurrentQuarter(firmHighlightsDTO.getEPSEstimateCurrentQuarter());
        firmEODHighlightsTO.setMostRecentQuarter(firmHighlightsDTO.getMostRecentQuarter());
        firmEODHighlightsTO.setProfitMargin(firmHighlightsDTO.getProfitMargin());
        firmEODHighlightsTO.setOperatingMarginTTM(firmHighlightsDTO.getOperatingMarginTTM());
        firmEODHighlightsTO.setReturnOnAssetsTTM(firmHighlightsDTO.getReturnOnAssetsTTM());
        firmEODHighlightsTO.setReturnOnEquityTTM(firmHighlightsDTO.getReturnOnEquityTTM());
        firmEODHighlightsTO.setRevenueTTM(firmHighlightsDTO.getRevenueTTM());
        firmEODHighlightsTO.setRevenuePerShareTTM(firmHighlightsDTO.getRevenuePerShareTTM());
        firmEODHighlightsTO.setQuarterlyRevenueGrowthYOY(firmHighlightsDTO.getQuarterlyRevenueGrowthYOY());
        firmEODHighlightsTO.setGrossProfitTTM(firmHighlightsDTO.getGrossProfitTTM());
        firmEODHighlightsTO.setDilutedEpsTTM(firmHighlightsDTO.getDilutedEpsTTM());
        firmEODHighlightsTO.setQuarterlyEarningsGrowthYOY(firmHighlightsDTO.getQuarterlyEarningsGrowthYOY());

        return firmEODHighlightsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initValuationMapper() {

    Converter<ValuationDTO, FirmEODValuationTO> toUppercase = new AbstractConverter<ValuationDTO, FirmEODValuationTO>() {

      @Override
      protected FirmEODValuationTO convert(ValuationDTO valuationDTO) {
        FirmEODValuationTO firmEODValuationTO = new FirmEODValuationTO();
        firmEODValuationTO.setDate(LocalDate.now());
        firmEODValuationTO.setForwardPE(valuationDTO.getForwardPE());
        firmEODValuationTO.setTrailingPE(valuationDTO.getTrailingPE());
        firmEODValuationTO.setPriceSalesTTM(valuationDTO.getPriceSalesTTM());
        firmEODValuationTO.setPriceBookMRQ(valuationDTO.getPriceBookMRQ());
        firmEODValuationTO.setEnterpriseValueRevenue(valuationDTO.getEnterpriseValueRevenue());
        firmEODValuationTO.setEnterpriseValueEbitda(valuationDTO.getEnterpriseValueEbitda());
        return firmEODValuationTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initShareStatsMapper() {

    Converter<SharesStatsDTO, FirmEODShareStatsTO> toUppercase = new AbstractConverter<SharesStatsDTO, FirmEODShareStatsTO>() {

      @Override
      protected FirmEODShareStatsTO convert(SharesStatsDTO sharesStatsDTO) {
        FirmEODShareStatsTO firmEODShareStatsTO = new FirmEODShareStatsTO();
        return firmEODShareStatsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initFirmMapper() {

    Converter<FirmDTO, FirmEODQuoteTO> toUppercase = new AbstractConverter<FirmDTO, FirmEODQuoteTO>() {

      @Override
      protected FirmEODQuoteTO convert(FirmDTO firmDTO) {
        FirmEODQuoteTO firmTO = new FirmEODQuoteTO();
        firmTO.setName(firmDTO.getName());
        firmTO.setCode(firmDTO.getCode());
        firmTO.setExchangeShortName(firmDTO.getExchange_short_name());
        firmTO.setDate(LocalDate.parse(firmDTO.getDate(), format1));
        firmTO.setMarketCapitalization(firmDTO.getMarketCapitalization());
        firmTO.setVolume(firmDTO.getVolume());
        firmTO.setAdjustedClose(firmDTO.getAdjusted_close());
        return firmTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
