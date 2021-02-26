package ch.nblotti.securities.firm.highlights;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Repository
interface FirmHighlightsRepository extends PagingAndSortingRepository<FirmHighlightsTO, Long> {

  public FirmHighlightsTO findTopByCodeOrderByDate(String code);

  @Query(value="DELETE FROM firm_eod_highlights fh where fh.date = ?1", nativeQuery = true)
  @Modifying
  @Transactional
  public void deleteByDateSql( @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
