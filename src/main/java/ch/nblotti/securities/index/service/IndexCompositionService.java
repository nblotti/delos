package ch.nblotti.securities.index.service;

import ch.nblotti.securities.index.respository.IndexCompositionRepository;
import ch.nblotti.securities.index.respository.eod.IndexCompositionEODRepository;
import ch.nblotti.securities.index.to.IndexCompositionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class IndexCompositionService {


  @Autowired
  IndexCompositionEODRepository indexCompositionEODRepository;
  @Autowired
  IndexCompositionRepository indexCompositionRepository;


  public boolean hasBeenListed(String index, String code) {
    List<IndexCompositionTO> firm = indexCompositionRepository.findByExchangeAndCodeFirm(index, code);
    return !firm.isEmpty();
  }


  public Collection<IndexCompositionTO> getSPCompositionAtDate(LocalDate localDate, String index) {
    return indexCompositionEODRepository.getIndexCompositionAtDate(localDate,index);
  }

  public Iterable<IndexCompositionTO> loadSPCompositionAtDate(LocalDate localDate,String index) {
    Collection<IndexCompositionTO> indexCompositionTOS = indexCompositionEODRepository.getIndexCompositionAtDate(localDate,index);
    return indexCompositionRepository.saveAll(indexCompositionTOS);
  }


}
