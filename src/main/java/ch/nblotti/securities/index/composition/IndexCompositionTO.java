package ch.nblotti.securities.index.composition;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "INDEX_COMPOSITION")
class IndexCompositionTO {


  @Id
  @SequenceGenerator(initialValue = 2000000, name = "index_composition_id_gen", sequenceName = "index_composition_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "index_composition_gen")
  private Integer id;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "code")
  String code;

  @Column(name = "exchange")
  String exchange;

  @Column(name = "name")
  String name;

  @Column(name = "sector")
  String sector;

  @Column(name = "industry")
  String industry;

  public IndexCompositionTO() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}


