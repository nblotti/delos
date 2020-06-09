package ch.nblotti.securities.index.respository;

import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.firm.to.IndexQuoteTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;

@RepositoryRestResource(path = "indexquote")
public interface IndexQuoteRepository extends PagingAndSortingRepository<IndexQuoteTO, Integer> {

  public Collection<IndexQuoteTO> findAllByDate(LocalDate date);

  public Collection<IndexQuoteTO> findByCode(String code);

  public Collection<IndexQuoteTO> findByCodeAndDateAfterOrderByDate(String code, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
