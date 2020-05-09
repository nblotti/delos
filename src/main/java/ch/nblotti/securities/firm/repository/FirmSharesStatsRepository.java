package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import ch.nblotti.securities.firm.to.FirmEODSharesStatsTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirmSharesStatsRepository extends CrudRepository<FirmEODSharesStatsTO, Long> {


}
