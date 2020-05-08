package ch.nblotti.securities.index.sp500.service;

import ch.nblotti.securities.firm.dto.FirmDTO;
import ch.nblotti.securities.firm.service.FirmService;
import ch.nblotti.securities.firm.to.FirmEODQuoteTO;
import ch.nblotti.securities.index.sp500.respository.IndexCompositionRepository;
import ch.nblotti.securities.index.sp500.respository.IndexSp500EODRepository;
import ch.nblotti.securities.index.sp500.to.IndexCompositionTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Sp500IndexService {

  private DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");


  @Autowired
  IndexSp500EODRepository indexSp500EODRepository;
  @Autowired
  IndexCompositionRepository indexCompositionRepository;

  @Autowired
  private FirmService firmService;



  public boolean hasBeenListed(String index, String code) {
    List<IndexCompositionTO> firm = indexCompositionRepository.findByIndexAndCodeFirm(index, code);
    return !firm.isEmpty();
  }


  public Collection<FirmEODQuoteTO> loadMarket(final String exchange) {

    LocalDate yesterday = LocalDate.now().minusDays(1);
    Collection<FirmEODQuoteTO> firmSaved = new ArrayList<>();

    List<FirmEODQuoteTO> firms = firmService.getExchangeDataForDate(exchange, yesterday);
    Iterable<FirmEODQuoteTO> newItemSaved = firmService.saveAllEODMarketQuotes(firms);
    newItemSaved.forEach(firmSaved::add);
    return firmSaved;
  }




}
