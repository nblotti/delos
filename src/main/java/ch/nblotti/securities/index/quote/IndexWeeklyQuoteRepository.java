package ch.nblotti.securities.index.quote;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface IndexWeeklyQuoteRepository extends ReadOnlyRepository<IndexWeeklyQuoteTO, Integer> {


  public Iterable<IndexWeeklyQuoteTO> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

  @Query(value = "REFRESH MATERIALIZED VIEW INDEX_WEEKLY_QUOTE", nativeQuery = true)
  @Modifying
  public void refreshMaterializedView();
}
