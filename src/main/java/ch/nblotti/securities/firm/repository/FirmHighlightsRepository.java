package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirmHighlightsRepository extends CrudRepository<FirmEODHighlightsTO, Long> {


}
