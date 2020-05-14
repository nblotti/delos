package ch.nblotti.securities.index.sp500.respository;

import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(path = "indexcomposition")
public interface IndexCompositionRepository extends PagingAndSortingRepository<Sp500IndexSectorIndustryTO, Integer> {

  void deleteByDate(LocalDate localDate);


  List<Sp500IndexSectorIndustryTO> findByExchangeAndCodeFirm(String exchange, String codeFirm);
}
