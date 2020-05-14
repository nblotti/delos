package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@RepositoryRestResource(path = "firmquotes")
public interface FirmQuoteRepository  extends PagingAndSortingRepository<FirmEODQuoteTO, Integer> {

  public Collection<FirmEODQuoteTO> findAllByDate(LocalDate date);
}
