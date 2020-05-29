package ch.nblotti.securities.firm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/firm")
public class FirmController {

  @Autowired
  RestTemplate restTemplate;

  @Value("${solr.server.uri}")
  private String searchServerHost;




  @PostMapping(value = "/search/")
  public String mirrorRest(@RequestBody String body ,@RequestHeader MultiValueMap<String, String> headers) throws URISyntaxException {

    String url = String.format("%s",searchServerHost);

    ResponseEntity<String> responseEntity =
      restTemplate.exchange(url, HttpMethod.POST,new HttpEntity<String>(body,headers), String.class);

    return responseEntity.getBody();


  }
}



