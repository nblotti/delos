package ch.nblotti.securities.dayoff;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time")
@Transactional
public class TimeController {


  @Autowired
  TimeService timeService;


  @PostMapping(value = "/")
  public TimeDTO save(@RequestBody TimeDTO entity) {

    return timeService.save(entity);
  }

  @GetMapping(value = "/")
  public Iterable<TimeDTO> findAll() {
    return timeService.findAll();
  }


}



