package ch.nblotti.securities.index.composition;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "INDEX_COMPOSITION")
class IndexCompositionTO {


  @Id
  @SequenceGenerator(initialValue = 2000000, name = "index_composition_gen", sequenceName = "index_composition_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "index_composition_gen")
  private Integer id;


  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column(name = "code")
  String code;

  @Column(name = "name")
  String name;

  @Column(name = "is_active_now")
  private boolean isActiveNow;


  @Column(name = "is_delisted")
  private boolean isDelisted;


  public IndexCompositionTO() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isActiveNow() {
    return isActiveNow;
  }

  public void setActiveNow(boolean activeNow) {
    isActiveNow = activeNow;
  }

  public boolean isDelisted() {
    return isDelisted;
  }

  public void setDelisted(boolean delisted) {
    isDelisted = delisted;
  }
}


