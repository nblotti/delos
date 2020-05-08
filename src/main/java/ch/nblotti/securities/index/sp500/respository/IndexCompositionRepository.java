package ch.nblotti.securities.index.sp500.respository;

import ch.nblotti.securities.index.sp500.to.IndexCompositionTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IndexCompositionRepository extends CrudRepository<IndexCompositionTO, Integer> {

  void deleteByDate(LocalDate localDate);


  List<IndexCompositionTO> findByIndexAndCodeFirm(String index, String code);
}
