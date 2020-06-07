package ch.nblotti.securities.index.respository.eod;

import ch.nblotti.securities.index.to.IndexCompositionTO;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexCompositionEODRepository {



  @Autowired
  private DateTimeFormatter format1;

  @Value("${index.component.api.url}")
  public String indexComponentUrl;



  public String conponentStr = "$.Components[*]";


  @Value("${spring.application.eod.api.key}")
  public String apiKey;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private RestTemplate restTemplate;


  public Collection<IndexCompositionTO> getIndexCompositionAtDate(LocalDate localDate, String index) {


    String finalUrl = String.format(indexComponentUrl, index, apiKey, localDate.format(format1), localDate.format(format1));

    final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

    DocumentContext jsonContext = JsonPath.parse(response.getBody());

    List<IndexCompositionDTO> firms = Arrays.asList(jsonContext.read(conponentStr, IndexCompositionDTO[].class));
    firms.stream().forEach(x -> x.setExchange(index));
    List<IndexCompositionTO> firmsTOs = firms.stream().map(x -> modelMapper.map(x, IndexCompositionTO.class)).collect(Collectors.toList());
    firmsTOs.stream().forEach(x -> x.setDate(localDate));

    return firmsTOs;
  }


  @PostConstruct
  public void initShareStatsMapper() {

    Converter<IndexCompositionDTO, IndexCompositionTO> toUppercase = new AbstractConverter<IndexCompositionDTO, IndexCompositionTO>() {

      @Override
      protected IndexCompositionTO convert(IndexCompositionDTO firmDTO) {
        IndexCompositionTO indexCompositionTO = new IndexCompositionTO();

        indexCompositionTO.setCodeFirm(firmDTO.getCode());
        indexCompositionTO.setIndustry(firmDTO.getIndustry());
        indexCompositionTO.setSector(firmDTO.getSector());
        indexCompositionTO.setExchange(firmDTO.getExchange());
        return indexCompositionTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

}


