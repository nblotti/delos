package ch.nblotti.securities.index.quote;


import ch.nblotti.securities.firm.quote.FirmPeriodicQuoteDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class IndexQuoteService {

  private static final Logger logger = Logger.getLogger("IndexQuoteService");

  public static final String INDEXES = "indexes";
  public static final String INDEX_MAP = "indexesMap";


  @Autowired
  private IndexQuoteRepository indexQuoteRepository;

  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  private IndexWeeklyQuoteRepository indexWeeklyQuoteRepository;
  @Autowired
  private IndexMonthlyQuoteRepository indexMonthlyQuoteRepository;


  @Autowired
  protected DateTimeFormatter format1;


  public IndexQuoteDTO save(IndexQuoteDTO entity) {

    IndexQuoteTO indexQuoteTO = modelMapper.map(entity, IndexQuoteTO.class);

    IndexQuoteTO saved = indexQuoteRepository.save(indexQuoteTO);

    return modelMapper.map(saved, IndexQuoteDTO.class);

  }


  public Collection<IndexQuoteDTO> saveAll(Collection<IndexQuoteDTO> indexQuoteDTOs) {


    List<IndexQuoteTO> firmQuoteTOS = indexQuoteDTOs.stream().map(x -> modelMapper.map(x, IndexQuoteTO.class)).collect(Collectors.toList());

    Iterable<IndexQuoteTO> saved = indexQuoteRepository.saveAll(firmQuoteTOS);

    return StreamSupport.stream(saved.spliterator(), false)
      .map(n -> modelMapper.map(n, IndexQuoteDTO.class))
      .collect(Collectors.toList());


  }


  public void refreshMaterializedView() {
    indexWeeklyQuoteRepository.refreshMaterializedView();
    indexMonthlyQuoteRepository.refreshMaterializedView();
  }

  public Iterable<FirmPeriodicQuoteDTO> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual( ChronoUnit type, LocalDate startDate) {
    switch (type) {
      case WEEKS:
        Iterable<IndexWeeklyQuoteTO> weekQuote = indexWeeklyQuoteRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual( startDate, startDate);
        return StreamSupport.stream(weekQuote.spliterator(), false)
          .map(n -> modelMapper.map(n, FirmPeriodicQuoteDTO.class))
          .collect(Collectors.toList());

      case MONTHS:
        Iterable<IndexMonthlyQuoteTO> monthQuote = indexMonthlyQuoteRepository.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, startDate);
        return StreamSupport.stream(monthQuote.spliterator(), false)
          .map(n -> modelMapper.map(n, FirmPeriodicQuoteDTO.class))
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }


}
