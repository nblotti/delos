package ch.nblotti.securities.index;

import ch.nblotti.securities.firm.FirmPO;
import ch.nblotti.securities.firm.FirmService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class Sp500IndexServiceTest {

  @TestConfiguration
  static class Sp500LoaderTestContextConfiguration {


    @Bean
    public Sp500IndexService sp500IndexService() {

      return new Sp500IndexService();

    }
  }

  @Autowired
  Sp500IndexService sp500IndexService;

  @MockBean
  FirmService firmService;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  public void load() {

    Collection<FirmPO> firms = mock(Collection.class);
    Iterator<FirmPO> firmPOOIterator = mock(Iterator.class);
    FirmPO firmPO = mock(FirmPO.class);
    when(firms.iterator()).thenReturn(firmPOOIterator);
    when(firmPOOIterator.hasNext()).thenReturn(true, false);
    when(firmPOOIterator.next()).thenReturn(firmPO);


    Sp500IndexService spyASp500IndexService = spy(sp500IndexService);
    doReturn(firms).when(spyASp500IndexService).getSPCompositionAtDate(anyString());

    spyASp500IndexService.load(2000, 2000, 1, 12);
    verify(spyASp500IndexService, times(252)).getSPCompositionAtDate(anyString());

  }

  @Test
  public void getSPCompositionAtDate() throws IOException, URISyntaxException {

    Path resource = Paths.get(getClass().getClassLoader().getResource("sp500.json").toURI());
    String expectedJson = Files.lines(resource).collect(Collectors.joining());
    ResponseEntity responseEntity = mock(ResponseEntity.class);

    when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);
    when(responseEntity.getBody()).thenReturn(expectedJson);
    Collection<FirmPO> firms = sp500IndexService.getSPCompositionAtDate("2020-03-03");

    Assert.assertEquals(506, firms.size());


  }

}
