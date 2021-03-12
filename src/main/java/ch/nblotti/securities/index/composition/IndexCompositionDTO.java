package ch.nblotti.securities.index.composition;


import java.time.LocalDate;

public class IndexCompositionDTO {

  private Integer id;

  private String code;

  private String name;

  private LocalDate startDate;

  private LocalDate endDate;

  private boolean isActiveNow;

  private boolean isDelisted;

  public IndexCompositionDTO() {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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


