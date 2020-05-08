package ch.nblotti.securities.index.sp500.respository;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IndexSp500EODRepository {


  private DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Value("${index.sp500.component.api.url}")
  public String sp500ComponentUrl;


  private final String EOD_INDEX_HISTORICAL = "&historical=1&from=%s&to=%s";

  public String path = "$.Components[*]";


  @Value("${spring.application.eod.api.key}")
  public String apiKey;

  @Autowired
  private RestTemplate restTemplate;

/*
  public Collection<FirmTO> getSPCurrentComposition() {

    Map<String, FirmTO> firms = new HashMap<>();
    String finalUrl = String.format(sp500ComponentUrl, apiKey);

    final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

    DocumentContext jsonContext = JsonPath.parse(response.getBody());
    List<Map<String, String>> jsonpathCreatorName = jsonContext.read(path);

    for (Map<String, String> date : jsonpathCreatorName) {
      if (!firms.containsKey(date.get("Code"))) {
        firms.put(date.get("Code"), new FirmTO(date.get("Code"), date.get("Name"), date.get("Exchange"), date.get("Sector"), date.get("Industry")));
      }
    }

    return firms.values();
  }

  public Collection<FirmTO> getSPCompositionAtDate(LocalDate localDate) {



    Map<String, FirmTO> firms = new HashMap<>();
    String finalUrl = String.format(sp500ComponentUrl + EOD_INDEX_HISTORICAL, apiKey, localDate.format(format1), localDate.format(format1));

    final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

    DocumentContext jsonContext = JsonPath.parse(response.getBody());
    List<Map<String, String>> jsonpathCreatorName = jsonContext.read(path);

    for (Map<String, String> date : jsonpathCreatorName) {
      if (!firms.containsKey(date.get("Code"))) {
        firms.put(date.get("Code"), new FirmTO(date.get("Code"), date.get("Name"), date.get("Exchange"), date.get("Sector"), date.get("Industry")));
      }
    }

    return firms.values();
  }

*/
}
