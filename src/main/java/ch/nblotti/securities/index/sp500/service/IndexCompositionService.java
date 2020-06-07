package ch.nblotti.securities.index.sp500.service;

import ch.nblotti.securities.index.sp500.respository.IndexCompositionRepository;
import ch.nblotti.securities.index.sp500.respository.eod.IndexSp500EODRepository;
import ch.nblotti.securities.index.sp500.to.IndexCompositionTO;
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
    List<IndexCompositionTO> firm = indexCompositionRepository.findByExchangeAndCodeFirm(index, code);
    return !firm.isEmpty();
  }


  public Collection<IndexCompositionTO> getSPCompositionAtDate(LocalDate localDate, String index) {
    return indexSp500EODRepository.getIndexCompositionAtDate(localDate,index);
  }

  public Iterable<IndexCompositionTO> loadSPCompositionAtDate(LocalDate localDate,String index) {
    Collection<IndexCompositionTO> indexCompositionTOS = indexSp500EODRepository.getIndexCompositionAtDate(localDate,index);
    return indexCompositionRepository.saveAll(indexCompositionTOS);
  }


}
