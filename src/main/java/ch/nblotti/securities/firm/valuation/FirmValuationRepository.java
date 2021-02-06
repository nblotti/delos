package ch.nblotti.securities.firm.valuation;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirmValuationRepository extends PagingAndSortingRepository<FirmValuationTO, Integer> {


  public FirmValuationTO findTopByCodeOrderByDate(String code);



}
