package ch.nblotti.securities.firm.quote;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
interface FirmQuoteRepository extends PagingAndSortingRepository<FirmQuoteTO, Integer> {


  public Collection<FirmQuoteTO> findAllByDate(LocalDate date);

  public Collection<FirmQuoteTO> findByCodeOrderByDateAsc(String code);

  public Collection<FirmQuoteTO> findByCodeAndDateAfterOrderByDate(String code, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
