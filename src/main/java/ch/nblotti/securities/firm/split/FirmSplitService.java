package ch.nblotti.securities.firm.split;


import ch.nblotti.securities.firm.quote.FirmQuoteDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class FirmSplitService {

  private static final Logger logger = Logger.getLogger("FirmSplitService");


  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected DateTimeFormatter format1;


  @Autowired
  FirmSplitRepository firmSplitRepository;


  public FirmSplitDTO save(FirmSplitDTO firmSplit) {


    FirmSplitTO FirmSplitTO = modelMapper.map(firmSplit, FirmSplitTO.class);

    FirmSplitTO saved = firmSplitRepository.save(FirmSplitTO);

    return modelMapper.map(saved, FirmSplitDTO.class);

  }


  @PostConstruct
  void initFirmSplitTOsMapper() {

    Converter<FirmSplitDTO, FirmSplitTO> toUppercase = new AbstractConverter<FirmSplitDTO, FirmSplitTO>() {

      @Override
      protected FirmSplitTO convert(FirmSplitDTO firmSplitDTO) {
        FirmSplitTO firmQuoteTO = new FirmSplitTO();
        firmQuoteTO.setCode(firmSplitDTO.getCode());
        firmQuoteTO.setDate(firmSplitDTO.getDate());
        firmQuoteTO.setSplit(firmSplitDTO.getSplit());
        firmQuoteTO.setExchange(firmSplitDTO.getExchange());
        firmQuoteTO.setId(firmSplitDTO.getId());
        firmQuoteTO.setRetry(firmSplitDTO.getRetry());
        return firmQuoteTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  void initFirmEODSplitTOsMapper() {

    Converter<FirmSplitTO, FirmSplitDTO> toUppercase = new AbstractConverter<FirmSplitTO, FirmSplitDTO>() {

      @Override
      protected FirmSplitDTO convert(FirmSplitTO firmSplitTO) {
        FirmSplitDTO firmSplitDTO = new FirmSplitDTO();
        firmSplitDTO.setCode(firmSplitTO.getCode());
        firmSplitDTO.setDate(firmSplitTO.getDate());
        firmSplitDTO.setSplit(firmSplitTO.getSplit());
        firmSplitDTO.setExchange(firmSplitTO.getExchange());
        firmSplitDTO.setId(firmSplitTO.getId());
        firmSplitDTO.setRetry(firmSplitTO.getRetry());
        return firmSplitDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  public List<FirmSplitDTO> findAllByDate(LocalDate localDate) {

    Iterable<FirmSplitTO> firmSplitTOS = firmSplitRepository.findAllByDate(localDate);

    return StreamSupport.stream(firmSplitTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmSplitDTO.class))
      .collect(Collectors.toList());

  }

  public List<FirmSplitDTO> saveAll(List<FirmSplitDTO> firmSplitDTOs) {

    List<FirmSplitTO> firmQuoteTOS = firmSplitDTOs.stream().map(x -> modelMapper.map(x, FirmSplitTO.class)).collect(Collectors.toList());

    Iterable<FirmSplitTO> saved = firmSplitRepository.saveAll(firmQuoteTOS);

    return StreamSupport.stream(saved.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmSplitDTO.class))
      .collect(Collectors.toList());

  }

  public List<FirmSplitDTO> findAll() {
    Iterable<FirmSplitTO> saved = firmSplitRepository.findAll();

    return StreamSupport.stream(saved.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmSplitDTO.class))
      .collect(Collectors.toList());
  }
}
