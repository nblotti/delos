package ch.nblotti.securities.firm.infos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
 interface FirmInfoRepository extends PagingAndSortingRepository<FirmInfoTO, Integer> {

  public FirmInfoTO findTopByCodeOrderByDate(String code);
}
