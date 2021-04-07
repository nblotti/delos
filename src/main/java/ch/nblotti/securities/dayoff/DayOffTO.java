package ch.nblotti.securities.dayoff;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dayoff")
@Getter
@Setter
@NoArgsConstructor
class DayOffTO {

  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_dayoff", sequenceName = "dayoff_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_dayoff")
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "mics")
  private String mics;

  @Column(name = "country")
  private String country;

  @Column(name = "currency")
  private String currency;

  @Column(name = "timezone")
  private String timezone;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "holiday")
  private String holiday;


}
