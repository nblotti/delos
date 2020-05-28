package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.TopMoversGainersLoosersTO;
import ch.nblotti.securities.firm.to.TopMoversVolumeTO;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(path = "topmoversgainersloosers")
public interface TopMoversGainersLoosersRepository extends ReadOnlyRepository< TopMoversGainersLoosersTO,String> {

  Iterable<TopMoversGainersLoosersTO> findFirst10ByOrderByPercentChangeDesc();
  Iterable<TopMoversGainersLoosersTO> findFirst10ByOrderByPercentChangeAsc();
}
