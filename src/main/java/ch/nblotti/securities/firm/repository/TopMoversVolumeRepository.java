package ch.nblotti.securities.firm.repository;

import ch.nblotti.securities.firm.to.TopMoversGainersLoosersTO;
import ch.nblotti.securities.firm.to.TopMoversVolumeTO;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "topmoversvolume")
public interface TopMoversVolumeRepository extends ReadOnlyRepository< TopMoversVolumeTO,String> {

  Iterable<TopMoversVolumeTO> findFirst10ByOrderByVolumeDesc();
}
