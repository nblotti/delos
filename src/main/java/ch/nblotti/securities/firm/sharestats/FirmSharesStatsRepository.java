package ch.nblotti.securities.firm.sharestats;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
 interface FirmSharesStatsRepository extends PagingAndSortingRepository<FirmShareStatsTO, Integer> {

  public FirmShareStatsTO findTopByCodeOrderByDate(String code);

}
