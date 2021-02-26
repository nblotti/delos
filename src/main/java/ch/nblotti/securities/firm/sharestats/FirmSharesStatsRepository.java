package ch.nblotti.securities.firm.sharestats;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Repository
 interface FirmSharesStatsRepository extends PagingAndSortingRepository<FirmShareStatsTO, Integer> {

  public FirmShareStatsTO findTopByCodeOrderByDate(String code);

  @Query(value="DELETE FROM firm_eod_share_stats fs where fs.date = ?1", nativeQuery = true)
  @Modifying
  @Transactional
  public void deleteByDateSql( @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
