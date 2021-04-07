package ch.nblotti.securities.dayoff;


import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Slf4j
@Service
public class DayOffService {


  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected DateTimeFormatter format1;

  @Autowired
  protected DayOffRepository dayOffRepository;


  public Iterable<DayOffDTO> findAll() {
    Iterable<DayOffTO> dayOffTOS = dayOffRepository.findAll();

    return StreamSupport.stream(dayOffTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, DayOffDTO.class))
      .collect(Collectors.toList());
  }

  public DayOffDTO save(DayOffDTO entity) {

    DayOffTO timeTO = modelMapper.map(entity, DayOffTO.class);
    DayOffTO saved = dayOffRepository.save(timeTO);

    return modelMapper.map(saved, DayOffDTO.class);


  }


  @PostConstruct
  void initFirmQuoteMapper() {

    Converter<DayOffDTO, DayOffTO> toUppercase = new AbstractConverter<DayOffDTO, DayOffTO>() {

      @Override
      protected DayOffTO convert(DayOffDTO eODDayOffDTO) {
        DayOffTO dayOffTO = new DayOffTO();
        dayOffTO.setName(eODDayOffDTO.getName());
        dayOffTO.setCode(eODDayOffDTO.getCode());
        dayOffTO.setMics(eODDayOffDTO.getMics());
        dayOffTO.setCountry(eODDayOffDTO.getCountry());
        dayOffTO.setCurrency(eODDayOffDTO.getCurrency());
        dayOffTO.setTimezone(eODDayOffDTO.getTimezone());
        dayOffTO.setDate(eODDayOffDTO.getDate());
        dayOffTO.setHoliday(eODDayOffDTO.getHoliday());
        return dayOffTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  void initDayOffMapper() {

    Converter<DayOffTO, DayOffDTO> toUppercase = new AbstractConverter<DayOffTO, DayOffDTO>() {

      @Override
      protected DayOffDTO convert(DayOffTO dayOffTO) {
        DayOffDTO dayOffDTO = new DayOffDTO();
        dayOffDTO.setName(dayOffTO.getName());
        dayOffDTO.setCode(dayOffTO.getCode());
        dayOffDTO.setMics(dayOffTO.getMics());
        dayOffDTO.setCountry(dayOffTO.getCountry());
        dayOffDTO.setCurrency(dayOffTO.getCurrency());
        dayOffDTO.setTimezone(dayOffTO.getTimezone());
        dayOffDTO.setDate(dayOffTO.getDate());
        dayOffDTO.setHoliday(dayOffTO.getHoliday());
        return dayOffDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
