package ch.nblotti.securities.dayoff;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dayoff")
@Transactional
public class DayOffController {


  @Autowired
  DayOffService dayOffService;


  @PostMapping(value = "/")
  public DayOffDTO save(@RequestBody DayOffDTO entity) {
    return dayOffService.save(entity);
  }

  @GetMapping(value = "/")
  public Iterable<DayOffDTO> findAll() {
    return dayOffService.findAll();
  }
}



