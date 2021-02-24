package ch.nblotti.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {


  @Autowired
  ConfigService configService;

  @GetMapping(value = "/")
  public Iterable<ConfigDTO> findAllByCodeAndType(@RequestParam String code, @RequestParam String type) {

    return configService.findAllByCodeAndType(code, type);

  }

  @PostMapping(value = "/")
  public ConfigDTO findAllByCodeAndType(@RequestBody ConfigDTO configDTO) {

    return configService.save(configDTO);

  }

  @PutMapping(value = "/")
  public ConfigDTO update(@RequestBody ConfigDTO configDTO) {

    return configService.save(configDTO);

  }


}



