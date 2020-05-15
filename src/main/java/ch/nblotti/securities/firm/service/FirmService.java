package ch.nblotti.securities.firm.service;


import ch.nblotti.securities.firm.repository.*;
import ch.nblotti.securities.firm.repository.eod.FirmEODRepository;
import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.firm.to.FirmEODShareStatsTO;
import ch.nblotti.securities.firm.to.FirmEODValuationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;


@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirmService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";


  @Autowired
  FirmQuoteRepository firmQuoteRepository;

  @Autowired
  private FirmEODRepository firmEODRepository;

  @Autowired
  private FirmHighlightsRepository firmHighlightsRepository;

  @Autowired
  private FirmSharesStatsRepository firmSharesStatsRepository;

  @Autowired
  private FirmValuationRepository firmValuationRepository;


  public FirmEODValuationTO getValuationByDateAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {

    return firmEODRepository.getValuationByDateAndFirm(runDate,firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());

  }

  public FirmEODHighlightsTO getHighlightsByDateAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {

    return firmEODRepository.getHighlightsByDateAndFirm(runDate,firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }

  public FirmEODShareStatsTO getSharesStatByDateAndFirm(LocalDate runDate, FirmEODQuoteTO firmEODQuoteTO) {
    return firmEODRepository.getSharesStatByDateAndExchangeAndFirm(runDate,firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }


  public List<FirmEODQuoteTO> getExchangeDataForDate(LocalDate localDate,String exchange ) {
    return firmEODRepository.getExchangeDataByDate(localDate,exchange );
  }


  public Iterable<FirmEODQuoteTO> saveAllEODMarketQuotes(List<FirmEODQuoteTO> firmsTOs) {
    return firmQuoteRepository.saveAll(firmsTOs);
  }

/*
  public void saveAllValuations(Collection<FirmEODValuationTO> valuations) {
    firmValuationRepository.saveAll(valuations);
  }

  public void saveAllHighlights(Collection<FirmEODHighlightsTO> highlights) {
    firmHighlightsRepository.saveAll(highlights);
  }

  public void saveAllSharesStats(Collection<FirmEODShareStatsTO> sharesStats) {
    firmSharesStatsRepository.saveAll(sharesStats);
  }

*/
  public <S extends FirmEODHighlightsTO> S save(S entity) {
    return firmHighlightsRepository.save(entity);
  }

  public <S extends FirmEODShareStatsTO> S save(S entity) {
    return firmSharesStatsRepository.save(entity);
  }

  public <S extends FirmEODValuationTO> S save(S entity) {
    return firmValuationRepository.save(entity);
  }
}
