package ch.nblotti.securities.index.sp500.service;

import ch.nblotti.securities.index.sp500.respository.IndexCompositionRepository;
import ch.nblotti.securities.index.sp500.respository.eod.IndexSp500EODRepository;
import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class Sp500IndexService {


  @Autowired
  IndexSp500EODRepository indexSp500EODRepository;
  @Autowired
  IndexCompositionRepository indexCompositionRepository;


  public boolean hasBeenListed(String index, String code) {
    List<Sp500IndexSectorIndustryTO> firm = indexCompositionRepository.findByExchangeAndCodeFirm(index, code);
    return !firm.isEmpty();
  }


  public Collection<Sp500IndexSectorIndustryTO> getSPCompositionAtDate(LocalDate localDate) {
    return indexSp500EODRepository.getSPCompositionAtDate(localDate);
  }

  public Iterable<Sp500IndexSectorIndustryTO> loadSPCompositionAtDate(LocalDate localDate) {
    Collection<Sp500IndexSectorIndustryTO> sp500IndexSectorIndustryTOS = indexSp500EODRepository.getSPCompositionAtDate(localDate);
    return indexCompositionRepository.saveAll(sp500IndexSectorIndustryTOS);
  }


}
