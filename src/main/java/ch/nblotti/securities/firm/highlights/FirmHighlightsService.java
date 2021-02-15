package ch.nblotti.securities.firm.highlights;


import ch.nblotti.securities.firm.quote.FirmQuoteDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class FirmHighlightsService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";



  @Autowired
  private FirmHighlightsRepository firmHighlightsRepository;


  @Autowired
  protected ModelMapper modelMapper;



  public FirmHighlightsDTO findTopByCodeOrderByDate(String code) {
    FirmHighlightsTO firmHighlightsDTO = firmHighlightsRepository.findTopByCodeOrderByDate(code);
    return modelMapper.map(firmHighlightsDTO, FirmHighlightsDTO.class);
  }

  public Iterable<FirmHighlightsDTO> findAll(Sort sort) {
    Iterable<FirmHighlightsTO> firmHighlightsDTOS = firmHighlightsRepository.findAll(sort);

    return StreamSupport.stream(firmHighlightsDTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmHighlightsDTO.class))
      .collect(Collectors.toList());
  }

  public Iterable<FirmHighlightsDTO> saveAll(Iterable<FirmHighlightsDTO> firmHighlightsDTO) {

    Iterable<FirmHighlightsTO> firmHighlightsTO = modelMapper.map(firmHighlightsDTO, new TypeToken<Iterable<FirmHighlightsTO>>() {}.getType());

    Iterable<FirmHighlightsTO> saved = firmHighlightsRepository.saveAll(firmHighlightsTO);

    return modelMapper.map(saved, new TypeToken<List<FirmHighlightsDTO>>() {}.getType());
  }



  public FirmHighlightsDTO save(FirmHighlightsDTO firmHighlightsDTO) {
    FirmHighlightsTO firmHighlightsTO = modelMapper.map(firmHighlightsDTO, FirmHighlightsTO.class);

    FirmHighlightsTO saved = firmHighlightsRepository.save(firmHighlightsTO);

    return modelMapper.map(saved, FirmHighlightsDTO.class);
  }


  @PostConstruct
  public void initFirmEODHighlightsMapper() {

    Converter<FirmHighlightsTO, FirmHighlightsDTO> toUppercase = new AbstractConverter<FirmHighlightsTO, FirmHighlightsDTO>() {

      @Override
      protected FirmHighlightsDTO convert(FirmHighlightsTO firmEODHighlightsTO) {
        FirmHighlightsDTO firmHighlightsDTO = new FirmHighlightsDTO();
        firmHighlightsDTO.setCode(firmEODHighlightsTO.getCode());
        firmHighlightsDTO.setExchange(firmEODHighlightsTO.getExchange());
        firmHighlightsDTO.setMarketCapitalization(firmEODHighlightsTO.getMarketCapitalization());
        firmHighlightsDTO.setMarketCapitalizationMln(firmEODHighlightsTO.getMarketCapitalizationMln());
        firmHighlightsDTO.seteBITDA(firmEODHighlightsTO.geteBITDA());
        firmHighlightsDTO.setpERatio(firmEODHighlightsTO.getpERatio());
        firmHighlightsDTO.setpEGRatio(firmEODHighlightsTO.getpEGRatio());
        firmHighlightsDTO.setWallStreetTargetPrice(firmEODHighlightsTO.getWallStreetTargetPrice());
        firmHighlightsDTO.setBookValue(firmEODHighlightsTO.getBookValue());
        firmHighlightsDTO.setDividendShare(firmEODHighlightsTO.getDividendShare());
        firmHighlightsDTO.setDividendYield(firmEODHighlightsTO.getDividendYield());
        firmHighlightsDTO.setEarningsShare(firmEODHighlightsTO.getEarningsShare());
        firmHighlightsDTO.setePSEstimateCurrentYear(firmEODHighlightsTO.getePSEstimateCurrentYear());
        firmHighlightsDTO.setePSEstimateNextYear(firmEODHighlightsTO.getePSEstimateNextYear());
        firmHighlightsDTO.setePSEstimateNextQuarter(firmEODHighlightsTO.getePSEstimateNextQuarter());
        firmHighlightsDTO.setePSEstimateCurrentQuarter(firmEODHighlightsTO.getePSEstimateCurrentQuarter());
        firmHighlightsDTO.setMostRecentQuarter(firmEODHighlightsTO.getMostRecentQuarter());
        firmHighlightsDTO.setProfitMargin(firmEODHighlightsTO.getProfitMargin());
        firmHighlightsDTO.setOperatingMarginTTM(firmEODHighlightsTO.getOperatingMarginTTM());
        firmHighlightsDTO.setReturnOnAssetsTTM(firmEODHighlightsTO.getReturnOnAssetsTTM());
        firmHighlightsDTO.setReturnOnEquityTTM(firmEODHighlightsTO.getReturnOnEquityTTM());
        firmHighlightsDTO.setRevenueTTM(firmEODHighlightsTO.getRevenueTTM());
        firmHighlightsDTO.setRevenuePerShareTTM(firmEODHighlightsTO.getRevenuePerShareTTM());
        firmHighlightsDTO.setQuarterlyRevenueGrowthYOY(firmEODHighlightsTO.getQuarterlyRevenueGrowthYOY());
        firmHighlightsDTO.setGrossProfitTTM(firmEODHighlightsTO.getGrossProfitTTM());
        firmHighlightsDTO.setDilutedEpsTTM(firmEODHighlightsTO.getDilutedEpsTTM());
        firmHighlightsDTO.setQuarterlyEarningsGrowthYOY(firmEODHighlightsTO.getQuarterlyEarningsGrowthYOY());
        firmHighlightsDTO.setDate(firmEODHighlightsTO.getDate());

        return firmHighlightsDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initFirmHighlightsTOMapper() {

    Converter<FirmHighlightsDTO, FirmHighlightsTO> toUppercase = new AbstractConverter<FirmHighlightsDTO, FirmHighlightsTO>() {

      @Override
      protected FirmHighlightsTO convert(FirmHighlightsDTO firmHighlightsDTO) {
        FirmHighlightsTO firmHighlightsTO = new FirmHighlightsTO();
        firmHighlightsTO.setCode(firmHighlightsDTO.getCode());
        firmHighlightsTO.setExchange(firmHighlightsDTO.getExchange());
        firmHighlightsTO.setMarketCapitalization(firmHighlightsDTO.getMarketCapitalization());
        firmHighlightsTO.setMarketCapitalizationMln(firmHighlightsDTO.getMarketCapitalizationMln());
        firmHighlightsTO.seteBITDA(firmHighlightsDTO.geteBITDA());
        firmHighlightsTO.setpERatio(firmHighlightsDTO.getpERatio());
        firmHighlightsTO.setpEGRatio(firmHighlightsDTO.getpEGRatio());
        firmHighlightsTO.setWallStreetTargetPrice(firmHighlightsDTO.getWallStreetTargetPrice());
        firmHighlightsTO.setBookValue(firmHighlightsDTO.getBookValue());
        firmHighlightsTO.setDividendShare(firmHighlightsDTO.getDividendShare());
        firmHighlightsTO.setDividendYield(firmHighlightsDTO.getDividendYield());
        firmHighlightsTO.setEarningsShare(firmHighlightsDTO.getEarningsShare());
        firmHighlightsTO.setePSEstimateCurrentYear(firmHighlightsDTO.getePSEstimateCurrentYear());
        firmHighlightsTO.setePSEstimateNextYear(firmHighlightsDTO.getePSEstimateNextYear());
        firmHighlightsTO.setePSEstimateNextQuarter(firmHighlightsDTO.getePSEstimateNextQuarter());
        firmHighlightsTO.setePSEstimateCurrentQuarter(firmHighlightsDTO.getePSEstimateCurrentQuarter());
        firmHighlightsTO.setMostRecentQuarter(firmHighlightsDTO.getMostRecentQuarter());
        firmHighlightsTO.setProfitMargin(firmHighlightsDTO.getProfitMargin());
        firmHighlightsTO.setOperatingMarginTTM(firmHighlightsDTO.getOperatingMarginTTM());
        firmHighlightsTO.setReturnOnAssetsTTM(firmHighlightsDTO.getReturnOnAssetsTTM());
        firmHighlightsTO.setReturnOnEquityTTM(firmHighlightsDTO.getReturnOnEquityTTM());
        firmHighlightsTO.setRevenueTTM(firmHighlightsDTO.getRevenueTTM());
        firmHighlightsTO.setRevenuePerShareTTM(firmHighlightsDTO.getRevenuePerShareTTM());
        firmHighlightsTO.setQuarterlyRevenueGrowthYOY(firmHighlightsDTO.getQuarterlyRevenueGrowthYOY());
        firmHighlightsTO.setGrossProfitTTM(firmHighlightsDTO.getGrossProfitTTM());
        firmHighlightsTO.setDilutedEpsTTM(firmHighlightsDTO.getDilutedEpsTTM());
        firmHighlightsTO.setQuarterlyEarningsGrowthYOY(firmHighlightsDTO.getQuarterlyEarningsGrowthYOY());
        firmHighlightsTO.setDate(firmHighlightsDTO.getDate());

        return firmHighlightsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

}
