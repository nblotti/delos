package ch.nblotti.securities.dayoff;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TimeRepository extends PagingAndSortingRepository<TimeTO, Integer> {

}
