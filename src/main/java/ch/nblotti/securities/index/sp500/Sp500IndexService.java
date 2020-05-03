package ch.nblotti.securities.index.sp500;

import ch.nblotti.securities.firm.FirmPO;
import ch.nblotti.securities.firm.FirmService;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class Sp500IndexService {

  @Value("${index.sp500.component.api.url}")
  public String sp500ComponentUrl;

  private final String EOD_INDEX_HISTORICAL = "&historical=1&from=%s&to=%s";

  public String path = "$.Components[*]";


  @Value("${spring.application.eod.api.key}")
  public String apiKey;

  @Autowired
  private RestTemplate restTemplate;



  @Autowired
  IndexCompositionRepository indexCompositionRepository;

  public void deleteByDate(LocalDate localDate) {
    indexCompositionRepository.deleteByDate(localDate);
  }


  public Iterable<IndexComposition> saveIndexCompositionAtDate(LocalDate localDate, Collection<FirmPO> firms) {


    Collection<IndexComposition> compositions = new ArrayList<>();
    for (FirmPO firmPO : firms) {
      IndexComposition indexComposition = new IndexComposition(localDate, "SP500", firmPO.getCode(), firmPO.getExchange());
      compositions.add(indexComposition);

    }
    return indexCompositionRepository.saveAll(compositions);


  }



  public Collection<FirmPO> getSPCurrentComposition() {

    Map<String, FirmPO> firms = new HashMap<>();
    String finalUrl = String.format(sp500ComponentUrl , apiKey);

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

  Collection<FirmPO> getSPCompositionAtDate(String inputDate) {

    Map<String, FirmPO> firms = new HashMap<>();
    String finalUrl = String.format(sp500ComponentUrl + EOD_INDEX_HISTORICAL, apiKey, inputDate, inputDate);

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



}
