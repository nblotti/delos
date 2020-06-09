package ch.nblotti.securities.index.service;

import ch.nblotti.securities.firm.repository.FirmQuoteRepository;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.firm.to.IndexQuoteTO;
import ch.nblotti.securities.index.respository.IndexCompositionRepository;
import ch.nblotti.securities.index.respository.IndexQuoteRepository;
import ch.nblotti.securities.index.respository.eod.IndexCompositionEODRepository;
import ch.nblotti.securities.index.respository.eod.IndexEODRepository;
import ch.nblotti.securities.index.to.IndexCompositionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class IndexService {

  @Autowired
  IndexEODRepository indexEODRepository;


  @Autowired
  IndexQuoteRepository indexQuoteRepository;


  @Autowired
  IndexCompositionEODRepository indexCompositionEODRepository;
  @Autowired
  IndexCompositionRepository indexCompositionRepository;


  public boolean hasBeenListed(String index, String code) {
    List<IndexCompositionTO> firm = indexCompositionRepository.findByExchangeAndCodeFirm(index, code);
    return !firm.isEmpty();
  }


  public Collection<IndexCompositionTO> getSPCompositionAtDate(LocalDate localDate, String index) {
    return indexCompositionEODRepository.getIndexCompositionAtDate(localDate, index);
  }

  public Iterable<IndexCompositionTO> loadSPCompositionAtDate(LocalDate localDate, String index) {
    Collection<IndexCompositionTO> indexCompositionTOS = indexCompositionEODRepository.getIndexCompositionAtDate(localDate, index);
    return indexCompositionRepository.saveAll(indexCompositionTOS);
  }

  public List<IndexQuoteTO> getIndexDataByDate(LocalDate fromDate, LocalDate toDate, String index) {
    return indexEODRepository.getIndexDataByDate(fromDate,toDate, index);
  }

  public <S extends IndexQuoteTO> Iterable<S> saveAll(Iterable<S> entities) {
    return indexQuoteRepository.saveAll(entities);
  }
}
