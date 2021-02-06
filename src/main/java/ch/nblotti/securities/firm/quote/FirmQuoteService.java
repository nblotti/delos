package ch.nblotti.securities.firm.quote;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class FirmQuoteService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";


  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected DateTimeFormatter format1;


  @Autowired
  FirmQuoteRepository firmQuoteRepository;

  @Autowired
  private EODFirmQuoteRepository EODFirmQuoteRepository;


  public List<FirmQuoteDTO> getExchangeDataForDate(LocalDate localDate, String exchange) {
    List<EODFirmQuoteDTO> eodFirmQuoteDTOS = EODFirmQuoteRepository.getExchangeDataByDate(localDate, exchange);
    List<FirmQuoteDTO> firmsTOs = eodFirmQuoteDTOS.stream().map(x -> modelMapper.map(x, FirmQuoteDTO.class)).collect(Collectors.toList());
    firmsTOs.stream().forEach(x -> x.setActualExchange(exchange));
    return firmsTOs;
  }


  public Iterable<FirmQuoteDTO> saveAllEODMarketQuotes(List<FirmQuoteDTO> firmsTOs) {


    List<FirmQuoteTO> firmQuoteTOS = firmsTOs.stream().map(x -> modelMapper.map(x, FirmQuoteTO.class)).collect(Collectors.toList());

    Iterable<FirmQuoteTO> saved = firmQuoteRepository.saveAll(firmQuoteTOS);

    return StreamSupport.stream(saved.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmQuoteDTO.class))
      .collect(Collectors.toList());

  }


  @PostConstruct
  public void initFirmQuoteTOsMapper() {

    Converter<FirmQuoteDTO, FirmQuoteTO> toUppercase = new AbstractConverter<FirmQuoteDTO, FirmQuoteTO>() {

      @Override
      protected FirmQuoteTO convert(FirmQuoteDTO firmQuoteDTO) {
        FirmQuoteTO firmQuoteTO = new FirmQuoteTO();
        firmQuoteTO.setName(firmQuoteDTO.getName());
        firmQuoteTO.setCode(firmQuoteDTO.getCode());
        firmQuoteTO.setExchangeShortName(firmQuoteDTO.getExchangeShortName());
        firmQuoteTO.setDate(firmQuoteDTO.getDate());
        firmQuoteTO.setMarketCapitalization(firmQuoteDTO.getMarketCapitalization());
        firmQuoteTO.setVolume(firmQuoteDTO.getVolume());
        firmQuoteTO.setAdjustedClose(firmQuoteDTO.getAdjustedClose());
        return firmQuoteTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmEODQuoteTOsMapper() {

    Converter<FirmQuoteTO, FirmQuoteDTO> toUppercase = new AbstractConverter<FirmQuoteTO, FirmQuoteDTO>() {

      @Override
      protected FirmQuoteDTO convert(FirmQuoteTO firmDTO) {
        FirmQuoteDTO firmQuoteTO = new FirmQuoteDTO();
        firmQuoteTO.setName(firmDTO.getName());
        firmQuoteTO.setCode(firmDTO.getCode());
        firmQuoteTO.setExchangeShortName(firmDTO.getExchangeShortName());
        firmQuoteTO.setDate(firmDTO.getDate());
        firmQuoteTO.setMarketCapitalization(firmDTO.getMarketCapitalization());
        firmQuoteTO.setVolume(firmDTO.getVolume());
        firmQuoteTO.setAdjustedClose(firmDTO.getAdjustedClose());
        return firmQuoteTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmQuoteMapper() {

    Converter<EODFirmQuoteDTO, FirmQuoteDTO> toUppercase = new AbstractConverter<EODFirmQuoteDTO, FirmQuoteDTO>() {

      @Override
      protected FirmQuoteDTO convert(EODFirmQuoteDTO firmDTO) {
        FirmQuoteDTO firmQuoteTO = new FirmQuoteDTO();
        firmQuoteTO.setName(firmDTO.getName());
        firmQuoteTO.setCode(firmDTO.getCode());
        firmQuoteTO.setExchangeShortName(firmDTO.getExchange_short_name());
        firmQuoteTO.setDate(LocalDate.parse(firmDTO.getDate(), format1));
        firmQuoteTO.setMarketCapitalization(firmDTO.getMarketCapitalization());
        firmQuoteTO.setVolume(firmDTO.getVolume());
        firmQuoteTO.setAdjustedClose(firmDTO.getAdjusted_close());
        return firmQuoteTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
