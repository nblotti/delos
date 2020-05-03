package ch.nblotti.securities.index.sp500;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Repository
interface IndexCompositionRepository extends CrudRepository<IndexComposition, Integer> {

  void deleteByDate(LocalDate localDate);
}
