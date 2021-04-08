package ch.nblotti.securities.firm.quote;


import ch.nblotti.securities.dayoff.DayOffDTO;
import ch.nblotti.securities.firm.split.FirmSplitDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
  FirmMonthlyQuoteRepository firmMonthlyQuoteRepository;

  @Autowired
  FirmWeeklyQuoteRepository firmWeeklyQuoteRepository;


  public Iterable<FirmPeriodicQuoteDTO> findAll(ChronoUnit type) {
    Iterable<FirmPeriodicQuoteDTO> firmPeriodicQuoteDTOS;
    switch (type) {
      case WEEKS:
        Iterable<FirmWeeklyQuoteTO> weekQuote = firmWeeklyQuoteRepository.findAll();
        return StreamSupport.stream(weekQuote.spliterator(), false)
          .map(n -> modelMapper.map(n, FirmPeriodicQuoteDTO.class))
          .collect(Collectors.toList());

      case MONTHS:
        Iterable<FirmMonthlyQuoteTO> monthQuote = firmMonthlyQuoteRepository.findAll();
        return StreamSupport.stream(monthQuote.spliterator(), false)
          .map(n -> modelMapper.map(n, FirmPeriodicQuoteDTO.class))
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  public void refreshMaterializedView() {
    firmWeeklyQuoteRepository.refreshMaterializedView();
    firmMonthlyQuoteRepository.refreshMaterializedView();
  }

  public Iterable<FirmPeriodicQuoteDTO> findAllByCodeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String code, ChronoUnit type, LocalDate startDate, LocalDate endDate) {
    switch (type) {
      case WEEKS:
        Iterable<FirmWeeklyQuoteTO> weekQuote = firmWeeklyQuoteRepository.findAllByCodeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(code, startDate, endDate);
        return StreamSupport.stream(weekQuote.spliterator(), false)
          .map(n -> modelMapper.map(n, FirmPeriodicQuoteDTO.class))
          .collect(Collectors.toList());

      case MONTHS:
        Iterable<FirmMonthlyQuoteTO> monthQuote = firmMonthlyQuoteRepository.findAllByCodeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(code, startDate, endDate);
        return StreamSupport.stream(monthQuote.spliterator(), false)
          .map(n -> modelMapper.map(n, FirmPeriodicQuoteDTO.class))
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

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
        firmQuoteTO.setId(firmQuoteDTO.getId());
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
        firmQuoteTO.setId(firmDTO.getId());
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
  void initFirmMonthlyQuoteDTOMapper() {

    Converter<FirmMonthlyQuoteTO, FirmPeriodicQuoteDTO> toUppercase = new AbstractConverter<FirmMonthlyQuoteTO, FirmPeriodicQuoteDTO>() {

      @Override
      protected FirmPeriodicQuoteDTO convert(FirmMonthlyQuoteTO firmMonthlyQuoteTO) {
        FirmPeriodicQuoteDTO firmPeriodicQuoteDTO = new FirmPeriodicQuoteDTO();
        firmPeriodicQuoteDTO.setId(firmMonthlyQuoteTO.getId());
        firmPeriodicQuoteDTO.setCode(firmMonthlyQuoteTO.getCode());
        firmPeriodicQuoteDTO.setType(firmMonthlyQuoteTO.getType());
        firmPeriodicQuoteDTO.setOrdinal(firmMonthlyQuoteTO.getMonth_number());
        firmPeriodicQuoteDTO.setExchangeShortName(firmMonthlyQuoteTO.getExchangeShortName());
        firmPeriodicQuoteDTO.setStartdate(firmMonthlyQuoteTO.getStartDate());
        firmPeriodicQuoteDTO.setEnddate(firmMonthlyQuoteTO.getEndDate());
        firmPeriodicQuoteDTO.setMedianAdjustedClose(firmMonthlyQuoteTO.getMedianAdjustedClose());
        firmPeriodicQuoteDTO.setMedianMarketCapitalization(firmMonthlyQuoteTO.getMedianMarketCapitalization());
        firmPeriodicQuoteDTO.setMedianVolume(firmMonthlyQuoteTO.getMedianVolume());
        firmPeriodicQuoteDTO.setAverageAdjustedClose(firmMonthlyQuoteTO.getAverageAdjustedClose());
        firmPeriodicQuoteDTO.setAverageMarketCapitalization(firmMonthlyQuoteTO.getAverageMarketCapitalization());
        firmPeriodicQuoteDTO.setAverageVolume(firmMonthlyQuoteTO.getAverageVolume());
        return firmPeriodicQuoteDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  void initFirmWeeklyQuoteDTOsMapper() {

    Converter<FirmWeeklyQuoteTO, FirmPeriodicQuoteDTO> toUppercase = new AbstractConverter<FirmWeeklyQuoteTO, FirmPeriodicQuoteDTO>() {

      @Override
      protected FirmPeriodicQuoteDTO convert(FirmWeeklyQuoteTO firmWeeklyQuoteTO) {
        FirmPeriodicQuoteDTO firmPeriodicQuoteDTO = new FirmPeriodicQuoteDTO();
        firmPeriodicQuoteDTO.setId(firmWeeklyQuoteTO.getId());
        firmPeriodicQuoteDTO.setCode(firmWeeklyQuoteTO.getCode());
        firmPeriodicQuoteDTO.setType(firmWeeklyQuoteTO.getType());
        firmPeriodicQuoteDTO.setOrdinal(firmWeeklyQuoteTO.getWeek_number());
        firmPeriodicQuoteDTO.setExchangeShortName(firmWeeklyQuoteTO.getExchangeShortName());
        firmPeriodicQuoteDTO.setStartdate(firmWeeklyQuoteTO.getStartDate());
        firmPeriodicQuoteDTO.setEnddate(firmWeeklyQuoteTO.getEndDate());
        firmPeriodicQuoteDTO.setMedianAdjustedClose(firmWeeklyQuoteTO.getMedianAdjustedClose());
        firmPeriodicQuoteDTO.setMedianMarketCapitalization(firmWeeklyQuoteTO.getMedianMarketCapitalization());
        firmPeriodicQuoteDTO.setMedianVolume(firmWeeklyQuoteTO.getMedianVolume());
        firmPeriodicQuoteDTO.setAverageAdjustedClose(firmWeeklyQuoteTO.getAverageAdjustedClose());
        firmPeriodicQuoteDTO.setAverageMarketCapitalization(firmWeeklyQuoteTO.getAverageMarketCapitalization());
        firmPeriodicQuoteDTO.setAverageVolume(firmWeeklyQuoteTO.getAverageVolume());
        return firmPeriodicQuoteDTO;
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
