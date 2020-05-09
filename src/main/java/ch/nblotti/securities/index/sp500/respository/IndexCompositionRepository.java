package ch.nblotti.securities.index.sp500.respository;

import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IndexCompositionRepository extends CrudRepository<Sp500IndexSectorIndustryTO, Integer> {

  void deleteByDate(LocalDate localDate);


  List<Sp500IndexSectorIndustryTO> findByIndexAndCodeFirm(String index, String code);
}
