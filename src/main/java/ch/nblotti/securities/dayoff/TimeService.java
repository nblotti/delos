package ch.nblotti.securities.dayoff;


import ch.nblotti.securities.firm.infos.FirmInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
public class TimeService {


  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected DateTimeFormatter format1;

  @Autowired
  protected TimeRepository timeRepository;


  public Iterable<TimeDTO> findAll() {
    Iterable<TimeTO> timeTOS = timeRepository.findAll();

    return StreamSupport.stream(timeTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, TimeDTO.class))
      .collect(Collectors.toList());
  }

  public TimeDTO save(TimeDTO entity) {

    TimeTO timeTO = modelMapper.map(entity, TimeTO.class);
    TimeTO saved = timeRepository.save(timeTO);

    return modelMapper.map(saved, TimeDTO.class);
  }


  @PostConstruct
  void initFirmQuoteMapper() {

    Converter<TimeDTO, TimeTO> toUppercase = new AbstractConverter<TimeDTO, TimeTO>() {

      @Override
      protected TimeTO convert(TimeDTO timeDTO) {
        TimeTO timeTO = new TimeTO();
        timeTO.setType(timeDTO.getType());
        timeTO.setStartDate(timeDTO.getStartDate());
        timeTO.setEndDate(timeDTO.getEndDate());
        timeTO.setOrdinal(timeDTO.getOrdinal());
        return timeTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  void initFirmDTOQuoteMapper() {

    Converter<TimeTO, TimeDTO> toUppercase = new AbstractConverter<TimeTO, TimeDTO>() {

      @Override
      protected TimeDTO convert(TimeTO timeTO) {
        TimeDTO timeDTO = new TimeDTO();
        timeDTO.setType(timeTO.getType());
        timeDTO.setStartDate(timeTO.getStartDate());
        timeDTO.setEndDate(timeTO.getEndDate());
        timeDTO.setOrdinal(timeTO.getOrdinal());
        return timeDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
