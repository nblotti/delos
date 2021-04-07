package ch.nblotti.securities.dayoff;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DayOffRepository extends PagingAndSortingRepository<DayOffTO, Integer> {

}
