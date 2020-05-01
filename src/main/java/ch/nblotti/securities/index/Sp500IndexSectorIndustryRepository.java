package ch.nblotti.securities.index;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
interface Sp500IndexSectorIndustryRepository extends CrudRepository<Sp500IndexSectorIndustryPO, Integer> {

  Iterable<Sp500IndexSectorIndustryPO> findAllBySectorAndIndustryAndDateAfter(String sector, String industry, LocalDate date);
}
