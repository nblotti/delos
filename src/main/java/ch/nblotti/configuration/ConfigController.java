package ch.nblotti.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

  @GetMapping(value = "/id")
  public ResponseEntity<ConfigDTO> findById(@RequestParam Long id) {

    Optional<ConfigDTO> returned = configService.findById(id);
    return returned.isPresent() ? ResponseEntity.ok(returned.get()) : ResponseEntity.notFound().build();

  }


  @DeleteMapping(value = "/id")
  public void deleteById(@RequestParam Long id) {
   configService.deleteById(id);

  }


  @PutMapping(value = "/")
  public ConfigDTO update(@RequestBody ConfigDTO configDTO) {

    return configService.save(configDTO);

  }


}



