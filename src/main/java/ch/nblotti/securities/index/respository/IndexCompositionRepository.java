package ch.nblotti.securities.index.respository;

import ch.nblotti.securities.index.to.IndexCompositionTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(path = "indexcomposition")
public interface IndexCompositionRepository extends PagingAndSortingRepository<IndexCompositionTO, Integer> {

  void deleteByDate(LocalDate localDate);


  List<IndexCompositionTO> findByExchangeAndCodeFirm(String exchange, String codeFirm);
  Iterable<IndexCompositionTO> findAllBySectorAndIndustryAndDateAfter(String sector, String industry, LocalDate date);

}
