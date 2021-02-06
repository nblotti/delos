package ch.nblotti.securities.firm.highlights;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
interface FirmHighlightsRepository extends PagingAndSortingRepository<FirmHighlightsTO, Long> {

  public FirmHighlightsTO findTopByCodeOrderByDate(String code);
}
