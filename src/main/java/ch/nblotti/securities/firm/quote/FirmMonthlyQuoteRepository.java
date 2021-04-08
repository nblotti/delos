package ch.nblotti.securities.firm.quote;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Repository
interface FirmMonthlyQuoteRepository extends ReadOnlyRepository<FirmMonthlyQuoteTO, Integer> {

  @Query(value = "REFRESH MATERIALIZED VIEW FIRM_MONTHLY_QUOTE", nativeQuery = true)
  @Modifying
  public void refreshMaterializedView();

  public Iterable<FirmMonthlyQuoteTO> findAllByCodeAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String code, LocalDate endDate, LocalDate startDate);
}
