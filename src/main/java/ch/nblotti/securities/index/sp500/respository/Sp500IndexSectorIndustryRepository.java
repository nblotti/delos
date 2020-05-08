package ch.nblotti.securities.index.sp500.respository;

import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface Sp500IndexSectorIndustryRepository extends CrudRepository<Sp500IndexSectorIndustryTO, Integer> {

  Iterable<Sp500IndexSectorIndustryTO> findAllBySectorAndIndustryAndDateAfter(String sector, String industry, LocalDate date);
}
