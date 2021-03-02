package ch.nblotti.securities.firm.quote;


import ch.nblotti.securities.firm.split.FirmSplitDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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


  public FirmQuoteDTO save(FirmQuoteDTO firmsTO) {


    FirmQuoteTO firmQuoteTOS = modelMapper.map(firmsTO, FirmQuoteTO.class);

    FirmQuoteTO saved = firmQuoteRepository.save(firmQuoteTOS);

    return modelMapper.map(saved, FirmQuoteDTO.class);

  }


  @PostConstruct
  void initFirmQuoteTOsMapper() {

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
  void initFirmEODQuoteTOsMapper() {

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

  public List<FirmQuoteDTO> findAllByCodeOrderByDateAsc(String code) {
    Collection<FirmQuoteTO> firmQuoteTOS = firmQuoteRepository.findByCodeOrderByDateAsc(code);

    return StreamSupport.stream(firmQuoteTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmQuoteDTO.class))
      .collect(Collectors.toList());

  }

  public void deleteByDate(LocalDate localDate) {
    firmQuoteRepository.deleteByDateSql(localDate);
  }
}
