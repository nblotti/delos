package ch.nblotti.securities.firm.quote;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface FirmWeeklyQuoteRepository extends ReadOnlyRepository<FirmWeeklyQuoteTO, Integer> {


  public Iterable<FirmWeeklyQuoteTO> findAllByCodeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String code,LocalDate startDate, LocalDate endDate);

  @Query(value = "REFRESH MATERIALIZED VIEW FIRM_WEEKLY_QUOTE", nativeQuery = true)
  @Modifying
  public void refreshMaterializedView();
}
