package ch.nblotti.securities.firm.split;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
interface FirmSplitRepository extends PagingAndSortingRepository<FirmSplitTO, Integer> {

  public Collection<FirmSplitTO> findAllByDate(LocalDate date);

}
