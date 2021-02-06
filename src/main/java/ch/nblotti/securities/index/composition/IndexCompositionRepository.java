package ch.nblotti.securities.index.composition;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
interface IndexCompositionRepository extends PagingAndSortingRepository<IndexCompositionTO, Long> {


}
