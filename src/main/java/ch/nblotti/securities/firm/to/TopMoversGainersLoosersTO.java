package ch.nblotti.securities.firm.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "mv_movers")
@Entity
public class TopMoversGainersLoosersTO implements Serializable {

  @Column(name = "date")
  private LocalDate date;

  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "exchange")
  private String exchange;

  @Column(name = "adjusted_close")
  private Double adjustedClose;


  @Column(name = "previous_adjusted_close")
  private Double previousadjustedClose;

  @Column(name = "last_move")
  private Double percentChange;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
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

  public Double getAdjustedClose() {
    return adjustedClose;
  }

  public void setAdjustedClose(double adjustedClose) {
    this.adjustedClose = adjustedClose;
  }

  public Double getPreviousadjustedClose() {
    return previousadjustedClose;
  }

  public void setPreviousadjustedClose(Double previousadjustedClose) {
    this.previousadjustedClose = previousadjustedClose;
  }

  public Double getPercentChange() {
    return percentChange;
  }

  public void setPercentChange(Double percentChange) {
    this.percentChange = percentChange;
  }
}
