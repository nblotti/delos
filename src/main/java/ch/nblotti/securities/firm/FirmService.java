package ch.nblotti.securities.firm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;


@Service
public class FirmService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";

  @Autowired
  private FirmRepository firmRepository;


  @Autowired
  private CacheManager cacheManager;

  @Autowired
  private MarketCapRepository marketCapRepository;


  public List<FirmPO> getFirmsByExchange(String exchange) {

    List<FirmPO> firms;

    Map<String, List<FirmPO>> cachedFirms;

    if (cacheManager.getCache(FIRMS).get(FIRM_MAP) == null) {
      cachedFirms = new HashMap<>();
      cacheManager.getCache(FIRMS).put(FIRM_MAP, cachedFirms);
    } else {
      cachedFirms = (Map<String, List<FirmPO>>) cacheManager.getCache(FIRMS).get(FIRM_MAP).get();
    }

    if (cachedFirms.containsKey(exchange))
      firms = cachedFirms.get(exchange);
    else {
      firms = new ArrayList<>();
      firmRepository.findAll().forEach(firmPO -> {
        if (firmPO.getCode().equalsIgnoreCase(exchange)) firms.add(firmPO);
      });
      cachedFirms = new HashMap<>();
      cachedFirms.put(exchange, firms);
      cacheManager.getCache(FIRMS).put(FIRM_MAP, cachedFirms);
    }
    return firms;
  }


  @Scheduled(fixedRate = 10800000)
  public void clearCache() {
    cacheManager.getCache(FIRMS).clear();

  }

  public Optional<FirmPO> findFirm(String exchange, String code) {

    List<FirmPO> firms = getFirmsByExchange(exchange);

    return firms.stream().filter(f -> f.getCode().equalsIgnoreCase(code)).findFirst();
  }

  public FirmPO save(FirmPO firmPO) {

    return firmRepository.save(firmPO);

  }

  public Optional<FirmPO> findByExchangeAndCode(String exchange, String code) {
    return firmRepository.findByExchangeAndCode(exchange, code);
  }

  public Iterable<MarketCapDto> saveMarketCaps(List<MarketCapDto> marketCapDtos) {
    return marketCapRepository.saveAll(marketCapDtos);

  }


  public Iterable<FirmPO> saveAll(List<FirmPO> firmPOS) {
    return firmRepository.saveAll(firmPOS);
  }
}
