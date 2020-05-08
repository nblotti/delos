package ch.nblotti.securities.firm.service;


import ch.nblotti.securities.firm.dto.FirmDTO;
import ch.nblotti.securities.firm.dto.SharesStatsDTO;
import ch.nblotti.securities.firm.dto.ValuationDTO;
import ch.nblotti.securities.firm.repository.*;
import ch.nblotti.securities.firm.to.FirmEODHighlightsTO;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.firm.to.FirmEODSharesStatsTO;
import ch.nblotti.securities.firm.to.FirmEODValuationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;


@Service
public class FirmService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";


  @Autowired
  FirmEODQuoteRepository firmEODQuoteRepository;

  @Autowired
  private FirmEODRepository firmEODRepository;

  @Autowired
  private FirmEODHighlightsRepository firmEODHighlightsRepository;

  @Autowired
  private FirmEODSharesStatsRepository firmEODSharesStatsRepository;

  @Autowired
  private FirmEODValuationRepository firmEODValuationRepository;


  public FirmEODValuationTO getValuation(FirmEODQuoteTO firmEODQuoteTO) {

    return firmEODRepository.getValuationForFirm(firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());

  }

  public FirmEODHighlightsTO getHighlights(FirmEODQuoteTO firmEODQuoteTO) {

    return firmEODRepository.getYesterdayHighlightsForFirm(firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }

  public FirmEODSharesStatsTO getSharesStat(FirmEODQuoteTO firmEODQuoteTO) {
    return firmEODRepository.getYesterdaySharesStatByExchangeAndFirm(firmEODQuoteTO.getExchangeShortName(), firmEODQuoteTO.getCode());
  }


  public List<FirmEODQuoteTO> getExchangeDataForDate(String exchange, LocalDate localDate) {
    return firmEODRepository.getExchangeDataForDate(exchange, localDate);
  }


  public Iterable<FirmEODQuoteTO> saveAllEODMarketQuotes(List<FirmEODQuoteTO> firmsTOs) {
    return firmEODQuoteRepository.saveAll(firmsTOs);
  }


  public void saveAllValuations(Collection<FirmEODValuationTO> valuations) {
    firmEODValuationRepository.saveAll(valuations);
  }

  public void saveAllHighlights(Collection<FirmEODHighlightsTO> highlights) {
    firmEODHighlightsRepository.saveAll(highlights);
  }

  public void saveAllSharesStats(Collection<FirmEODSharesStatsTO> sharesStats) {
    firmEODSharesStatsRepository.saveAll(sharesStats);
  }


}
