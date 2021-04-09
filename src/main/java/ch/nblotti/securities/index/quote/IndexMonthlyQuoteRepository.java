package ch.nblotti.securities.index.quote;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface IndexMonthlyQuoteRepository extends ReadOnlyRepository<IndexMonthlyQuoteTO, Integer> {

  @Query(value = "REFRESH MATERIALIZED VIEW INDEX_MONTHLY_QUOTE", nativeQuery = true)
  @Modifying
  public void refreshMaterializedView();

  public Iterable<IndexMonthlyQuoteTO> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual( LocalDate startDate,LocalDate endDate);
}
