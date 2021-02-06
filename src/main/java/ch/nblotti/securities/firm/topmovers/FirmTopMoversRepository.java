package ch.nblotti.securities.firm.topmovers;

import ch.nblotti.securities.firm.common.ReadOnlyRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
interface FirmTopMoversRepository extends ReadOnlyRepository<FirmTopMoversTO, String> {

  @Cacheable("firmFirst10ByVolume")
  Iterable<FirmTopMoversTO> findFirst10ByCurrentExchangeOrderByVolumeDesc(String exchange);

  @Cacheable("firmFirst10ByPercentDesc")
  Iterable<FirmTopMoversTO> findFirst10ByCurrentExchangeOrderByPercentChangeDesc(String exchange);

  @Cacheable("firmFirst10ByPercentAsc")
  Iterable<FirmTopMoversTO> findFirst10ByCurrentExchangeOrderByPercentChangeAsc(String exchange);

  FirmTopMoversTO findByCode(String code);
}
