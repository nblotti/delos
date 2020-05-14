package ch.nblotti.securities.firm.to;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CONFIG")
public class ConfigTO {

  @Id
  @SequenceGenerator(initialValue = 2000000, name = "config_id_gen", sequenceName = "config_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_id_gen")
  private Integer id;


  @Column(name = "CODE")
  private String code;

  @Column(name = "TYPE")
  private String type;

  @Column(name = "KEY")
  private String key;

  @Lob
  @Column(name = "VALUE")
  private String value;


  public ConfigTO() {
  }

  public ConfigTO(String code, String type, String key, String value) {
    this.code = code;
    this.type = type;
    this.key = key;
    this.value = value;
  }

  public Integer getId() {
    return id;
  }
}
