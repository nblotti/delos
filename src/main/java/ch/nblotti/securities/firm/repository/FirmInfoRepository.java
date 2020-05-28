package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODInfoTO;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;

@RepositoryRestResource(path = "firminfo")
public interface FirmInfoRepository extends PagingAndSortingRepository<FirmEODInfoTO, Integer> {

}
