package ch.nblotti.securities.firm.quote;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Repository
interface FirmQuoteRepository extends PagingAndSortingRepository<FirmQuoteTO, Integer> {


  public Collection<FirmQuoteTO> findAllByDate(LocalDate date);

  public Collection<FirmQuoteTO> findByCodeOrderByDateAsc(String code);

  public Collection<FirmQuoteTO> findByCodeAndDateAfterOrderByDate(String code, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

  @Query(value="DELETE FROM firm_eod_quote fq where fq.date = ?1", nativeQuery = true)
  @Modifying
  @Transactional
  public void deleteByDateSql( @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
