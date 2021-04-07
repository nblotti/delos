package ch.nblotti.securities.dayoff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {

  LocalDate startDate;
  LocalDate endDate;
  ChronoUnit type;
  int ordinal;

}
