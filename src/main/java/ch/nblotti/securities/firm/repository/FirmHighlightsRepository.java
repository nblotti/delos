package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@RepositoryRestResource(path = "firmhighlights")
public interface FirmHighlightsRepository extends PagingAndSortingRepository<FirmEODHighlightsTO, Long> {


}
