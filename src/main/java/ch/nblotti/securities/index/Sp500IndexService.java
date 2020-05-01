package ch.nblotti.securities.index;

import ch.nblotti.securities.firm.FirmPO;
import ch.nblotti.securities.firm.FirmService;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class Sp500IndexService {

  @Value("${index.sp500.component.api.url}")
  public String sp500ComponentUrl;

  public String path = "$.Components[*]";


  @Value("${spring.application.eod.api.key}")
  public String apiKey;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private FirmService firmService;

  @Autowired
  IndexCompositionRepository indexCompositionRepository;

  public void load(Integer startYear, Integer endYear, Integer startMonth, Integer endMonth) {

    DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    int december = 12;

    for (int year = startYear; year <= endYear; year++) {
      if (year == endYear)
        december = endMonth;
      for (int month = startMonth; month <= december; month++) {
        LocalDate localDate = localDate(year, month);

        Collection<FirmPO> firms = getSPCompositionAtDate(localDate.format(format1));

        saveIndexComposition(firms, localDate);
      }

    }

  }

  private LocalDate localDate(int year, int month) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

    return convertToLocalDateViaInstant(cal.getTime());

  }

  public void deleteIndexCompositionAtDate(int year, int month) {

    LocalDate localDate = localDate(year, month);
    deleteIndexCompositionAtDate(localDate);
  }


  private void deleteIndexCompositionAtDate(LocalDate localDate) {

    indexCompositionRepository.deleteByDate(localDate);
  }

  private void saveIndexComposition(Collection<FirmPO> firms, LocalDate localDate) {

    saveFirmIfNotExist(firms);
    deleteIndexCompositionAtDate(localDate);
    saveIndexCompositionAtDate(localDate, firms);
  }

  private Iterable<IndexComposition> saveIndexCompositionAtDate(LocalDate localDate, Collection<FirmPO> firms) {


    Collection<IndexComposition> compositions = new ArrayList<>();
    for (FirmPO firmPO : firms) {
      IndexComposition indexComposition = new IndexComposition(localDate, "SP500", firmPO.getCode(), firmPO.getExchange());
      compositions.add(indexComposition);

    }
    return indexCompositionRepository.saveAll(compositions);


  }

  private void saveFirmIfNotExist(Collection<FirmPO> firms) {

    for (FirmPO firm : firms) {
      Optional<FirmPO> dbFirm = firmService.findByExchangeAndCode(firm.getExchange(), firm.getCode());
      if (!dbFirm.isPresent()) {
        firmService.save(firm);
      }
    }
  }

  Collection<FirmPO> getSPCompositionAtDate(String inputDate) {

    Map<String, FirmPO> firms = new HashMap<>();
    String finalUrl = String.format(sp500ComponentUrl, apiKey, inputDate, inputDate);

    final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

    DocumentContext jsonContext = JsonPath.parse(response.getBody());
    List<Map<String, String>> jsonpathCreatorName = jsonContext.read(path);

    for (Map<String, String> date : jsonpathCreatorName) {
      if (!firms.containsKey(date.get("Code"))) {
        firms.put(date.get("Code"), new FirmPO(date.get("Code"), date.get("Name"), date.get("Exchange"), date.get("Sector"), date.get("Industry")));
      }
    }

    return firms.values();
  }

  private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
  }

}
