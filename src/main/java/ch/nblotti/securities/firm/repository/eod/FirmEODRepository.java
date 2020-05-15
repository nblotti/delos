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
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
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
  Cache cacheOne;

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


  public FirmEODValuationTO getValuationByDateAndFirm(LocalDate runDate, String exchange, String symbol) {


    logger.log(Level.INFO, String.format("%s - Loading valuation details for %s %s ", runDate.format(format1), exchange, symbol));

    boolean networkErrorHandling = false;
    while (!networkErrorHandling) {
      try {
        String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
        final ResponseEntity<String> response = getStringResponseEntity(finalUrl);
        DocumentContext content = JsonPath.parse(response.getBody());
        ValuationDTO valuationDTO = content.read(valuationStr, ValuationDTO.class);

        FirmEODValuationTO fVpost = modelMapper.map(valuationDTO, FirmEODValuationTO.class);
        fVpost.setExchange(exchange);
        fVpost.setDate(runDate);
        fVpost.setCode(symbol);
        return fVpost;
      } catch (Exception ex) {
        logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
      }
    }


    throw new IllegalStateException();

  }

  public FirmEODHighlightsTO getHighlightsByDateAndFirm(LocalDate runDate, String exchange, String symbol) {

    logger.log(Level.INFO, String.format("%s - Loading highlights details for %s %s ", runDate.format(format1), exchange, symbol));


    String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
    boolean networkErrorHandling = false;
    while (!networkErrorHandling) {
      try {
        final ResponseEntity<String> response = getStringResponseEntity(finalUrl);
        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        FirmHighlightsDTO firmHighlightsDTO = jsonContext.read(highlightStr, FirmHighlightsDTO.class);
        FirmEODHighlightsTO fHpost = modelMapper.map(firmHighlightsDTO, FirmEODHighlightsTO.class);
        fHpost.setExchange(exchange);
        fHpost.setDate(runDate);
        fHpost.setCode(symbol);

        return fHpost;
      } catch (Exception ex) {
        logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
      }
    }
    throw new IllegalStateException();

  }

  private ResponseEntity<String> getStringResponseEntity(String finalUrl) {
    if (cacheOne.get(finalUrl.hashCode()) == null) {
      ResponseEntity<String> entity = restTemplate.getForEntity(finalUrl, String.class);
      cacheOne.put(finalUrl.hashCode(), entity);
      return entity;
    }

    return (ResponseEntity<String>) cacheOne.get(finalUrl.hashCode()).get();
  }

  public FirmEODShareStatsTO getSharesStatByDateAndExchangeAndFirm(LocalDate runDate, String exchange, String symbol) {

    logger.log(Level.INFO, String.format("%s - Loading ShareStats details for %s %s ", runDate.format(format1), exchange, symbol));


    boolean networkErrorHandling = false;
    while (!networkErrorHandling) {
      try {
        String finalUrl = String.format(firmUrl, symbol, exchange, apiKey);
        final ResponseEntity<String> response = getStringResponseEntity(finalUrl);
        DocumentContext jsonContext = JsonPath.parse(response.getBody());

        SharesStatsDTO sharesStatsDTO = jsonContext.read(sharesStatStr, SharesStatsDTO.class);

        FirmEODShareStatsTO fSpost = modelMapper.map(sharesStatsDTO, FirmEODShareStatsTO.class);
        fSpost.setExchange(exchange);
        fSpost.setDate(runDate);
        fSpost.setCode(symbol);

        return fSpost;
      } catch (Exception ex) {
        logger.log(Level.INFO, String.format("Error, retrying\r\n%s", ex.getMessage()));
      }
    }
    throw new IllegalStateException();

  }


  @Autowired
  private RestTemplate restTemplate;


  public List<FirmEODQuoteTO> getExchangeDataByDate(LocalDate runDate, String exchange) {

    DocumentContext jsonContext = null;
    boolean networkErrorHandling = false;
    while (!networkErrorHandling) {
      try {
        String finalUrl = String.format(marketCap, exchange, apiKey, runDate.format(format1));
        final ResponseEntity<String> response = getStringResponseEntity(finalUrl);
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
