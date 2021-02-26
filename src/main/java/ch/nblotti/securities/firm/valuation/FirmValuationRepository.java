package ch.nblotti.securities.firm.valuation;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Repository
public interface FirmValuationRepository extends PagingAndSortingRepository<FirmValuationTO, Integer> {


  public FirmValuationTO findTopByCodeOrderByDate(String code);


  @Query(value="DELETE FROM firm_eod_valuation fv where fv.date = ?1", nativeQuery = true)
  @Modifying
  @Transactional
  public void deleteByDateSql( @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
