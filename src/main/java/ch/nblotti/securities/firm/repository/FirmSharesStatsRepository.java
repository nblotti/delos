package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODShareStatsTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "firmsharestats")
public interface FirmSharesStatsRepository extends PagingAndSortingRepository<FirmEODShareStatsTO, Integer> {


}
