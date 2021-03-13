package ch.nblotti.securities.index.topmovers;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.cache.annotation.Cacheable;

public interface IndexTopMoversRepository extends ReadOnlyRepository<IndexTopMoversTO, String> {

  @Cacheable("indicesMovers")
  Iterable<IndexTopMoversTO> findAllByNbrDays(int nbrDays);
}
