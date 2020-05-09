package ch.nblotti.securities.index.sp500.respository.eod;

import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
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
public class IndexSp500EODRepository {


  private DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Value("${index.sp500.component.api.url}")
  public String sp500ComponentUrl;


  private final String EOD_INDEX_SP500 = "GSPC.INDX";

  public String conponentStr = "$.Components[*]";


  @Value("${spring.application.eod.api.key}")
  public String apiKey;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private RestTemplate restTemplate;


  public Collection<Sp500IndexSectorIndustryTO> getSPCompositionAtDate(LocalDate localDate) {


    String finalUrl = String.format(sp500ComponentUrl + EOD_INDEX_SP500, apiKey, localDate.format(format1), localDate.format(format1));

    final ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);

    DocumentContext jsonContext = JsonPath.parse(response.getBody());

    List<FirmDTO> firms = Arrays.asList(jsonContext.read(conponentStr, FirmDTO[].class));
    List<Sp500IndexSectorIndustryTO> firmsTOs = firms.stream().map(x -> modelMapper.map(x, Sp500IndexSectorIndustryTO.class)).collect(Collectors.toList());
    firmsTOs.stream().forEach(x -> x.setDate(localDate));

    return firmsTOs;
  }


  @PostConstruct
  public void initShareStatsMapper() {

    Converter<FirmDTO, Sp500IndexSectorIndustryTO> toUppercase = new AbstractConverter<FirmDTO, Sp500IndexSectorIndustryTO>() {

      @Override
      protected Sp500IndexSectorIndustryTO convert(FirmDTO firmDTO) {
        Sp500IndexSectorIndustryTO sp500IndexSectorIndustryTO = new Sp500IndexSectorIndustryTO();

        sp500IndexSectorIndustryTO.setCodeFirm(firmDTO.getCode());
        sp500IndexSectorIndustryTO.setIndustry(firmDTO.getSector());
        sp500IndexSectorIndustryTO.setSector(firmDTO.getSector());
        sp500IndexSectorIndustryTO.setExchange(firmDTO.getExchange());
        return sp500IndexSectorIndustryTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

}


