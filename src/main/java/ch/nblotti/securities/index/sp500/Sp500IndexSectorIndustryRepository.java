package ch.nblotti.securities.index.sp500;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface Sp500IndexSectorIndustryRepository extends CrudRepository<Sp500IndexSectorIndustryPO, Integer> {

  Iterable<Sp500IndexSectorIndustryPO> findAllBySectorAndIndustryAndDateAfter(String sector, String industry, LocalDate date);
}
