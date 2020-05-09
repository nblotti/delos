package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODSharesStatsTO;
import ch.nblotti.securities.firm.to.FirmEODValuationTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirmValuationRepository extends CrudRepository<FirmEODValuationTO, Long> {


}
