package ch.nblotti.securities.firm;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/firm")
public class FirmController {


  @Autowired
  private FirmService firmService;


  @GetMapping(value = "/{exchange}/{code}")
  public Optional<FirmPO> findFirmByExchangeAndCode(@PathVariable String exchange, @PathVariable String code) {

    return firmService.findFirm(exchange, code);


  }

  @PutMapping(value = "/")
  public FirmPO save(@RequestBody @Valid FirmPO firm) {

    return firmService.save(firm);


  }

  @GetMapping(value = "/")
  public Iterable<FirmPO> saveAll() {

    List<FirmPO> firmPOS = new ArrayList<>();

    for (int i = 0; i <= 10; i++) {
      FirmPO p = new FirmPO(String.valueOf(i), "US", String.valueOf(i), "Basic Materials", "Agricultural Inputs");
      firmPOS.add(p);

    }
    return firmService.saveAll(firmPOS);
  }


  @PostMapping(value = "/marketcap/{exchange}/{firm}")
  public Iterable<MarketCapDto> setMarketCap(@PathVariable(name = "exchange") String exchange, @PathVariable(name = "firm") String firm, @RequestBody List<MarketCapDto> marketcaps) {

    marketcaps.stream().forEach(marketCapDto -> {
      marketCapDto.setExchange(exchange);
      marketCapDto.setCode(firm);
    });
    return firmService.saveMarketCaps(marketcaps);

  }


}
