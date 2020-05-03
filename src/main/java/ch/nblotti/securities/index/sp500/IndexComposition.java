package ch.nblotti.securities.index.sp500;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "INDEX_COMPOSITION")
class IndexComposition {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @JsonProperty("DATE")
  private LocalDate date;

  @NotNull
  @JsonProperty("INDEX")
  private String index;
  @NotNull
  @JsonProperty("CODE_FIRM")
  private String codeFirm;

  @JsonProperty("EXCHANGE")
  private String exchange;

  public IndexComposition() {
  }

  public IndexComposition(@NotNull LocalDate date, @NotNull String index, @NotNull String codeFirm, String exchange) {
    this.date = date;
    this.index = index;
    this.codeFirm = codeFirm;
    this.exchange = exchange;
  }

  public Integer getId() {
    return id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getCodeFirm() {
    return codeFirm;
  }

  public void setCodeFirm(String firmCode) {
    this.codeFirm = firmCode;
  }

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }
}
