package ch.nblotti.securities.index.respository.eod;

import ch.nblotti.securities.firm.repository.common.eod.AbstractEODQuoteRepository;
import ch.nblotti.securities.firm.to.IndexQuoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@Component
public class IndexEODRepository extends AbstractEODQuoteRepository {


  private static final Logger logger = Logger.getLogger("IndexEODRepository");

  @Autowired
  private DateTimeFormatter format1;


  public static final String INDEX_SUFFIX = "INDX";

  @Value("${index.quote.url}")
  private String indexUrl;
  public String indexHistoryStr = "$.[*]";


  public List<IndexQuoteTO> getIndexDataByDate(LocalDate fromDate, LocalDate toDate, String index) {

    String finalUrl = String.format(indexUrl, index, INDEX_SUFFIX, fromDate.format(format1), toDate.format(format1), apiKey);
    List<IndexQuoteTO> firmsTOs = this.getDataByDate(IndexQuoteTO.class, finalUrl, indexHistoryStr);
    firmsTOs.stream().forEach(x -> x.setCode(index));
    return firmsTOs;
  }


}
