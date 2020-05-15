package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODValuationTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@RepositoryRestResource(path = "firmvaluation")
public interface FirmValuationRepository extends PagingAndSortingRepository<FirmEODValuationTO, Long> {


}
