package ch.nblotti.securities.index.topmovers;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;

public interface SP500TopMoversRepository extends ReadOnlyRepository<SP500TopMoversTO, Integer> {

  @Cacheable("findFSPirst10TopLoosers")
  @Query("select sp from SP500TopMoversTO sp where sp.viewType =1 and sp.myRank <= 10")
  Iterable<SP500TopMoversTO> findFSPirst10TopLoosers();


  @Cacheable("findFSPirst3TopLoosers")
  @Query("select sp from SP500TopMoversTO sp where sp.viewType =1 and sp.myRank <= 3")
  Iterable<SP500TopMoversTO> findFSPirst3TopLoosers();

  @Cacheable("findFSPirst10TopWiners")
  @Query("select sp from SP500TopMoversTO sp where sp.viewType =2 and sp.myRank <= 10")
  Iterable<SP500TopMoversTO> findFSPirst10TopWiners();


  @Cacheable("findFSPirst3TopWiners")
  @Query("select sp from SP500TopMoversTO sp where sp.viewType =2 and sp.myRank <= 3")
  Iterable<SP500TopMoversTO> findFSPirst3TopWiners();

  @Cacheable("findFSPirst10TopMovers")
  @Query("select sp from SP500TopMoversTO sp where sp.viewType =3 and sp.myRank <= 10")
  Iterable<SP500TopMoversTO> findFSPirst10TopMovers();

  @Cacheable("findFSPirst3TopMovers")
  @Query("select sp from SP500TopMoversTO sp where sp.viewType =3 and sp.myRank <= 3")
  Iterable<SP500TopMoversTO> findFSPirst3TopMovers();


}
