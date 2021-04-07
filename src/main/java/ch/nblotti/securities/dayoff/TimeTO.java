package ch.nblotti.securities.dayoff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "time")
@Getter
@Setter
@NoArgsConstructor
class TimeTO {

  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_time", sequenceName = "time_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_time")
  private Integer id;

  @Column(name = "STARTDATE")
  private LocalDate startDate;

  @Column(name = "ENDDATE")
  private LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private ChronoUnit type;

  @Column(name = "ordinal")
  private int ordinal;

}
