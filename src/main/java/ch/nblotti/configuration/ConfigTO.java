package ch.nblotti.configuration;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CONFIG")
public class ConfigTO {

  @Id
  @SequenceGenerator(initialValue = 2000000, name = "config_id_gen", sequenceName = "config_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "config_id_gen")
  private Long id;

  @Column(name = "CODE")
  private String code;

  @Column(name = "TYPE")
  private String type;


  @Lob
  @Column(name = "VALUE")
  private String value;


  public ConfigTO() {
  }

  public ConfigTO(String code, String type,  String value) {
    this.code = code;
    this.type = type;
    this.value = value;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
