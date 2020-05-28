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
  private double adjustedClose;


  @Column(name = "previous_adjusted_close")
  private double previousadjustedClose;

  @Column(name = "last_move")
  private double percentChange;

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

  public double getAdjustedClose() {
    return adjustedClose;
  }

  public void setAdjustedClose(double adjustedClose) {
    this.adjustedClose = adjustedClose;
  }

  public double getPreviousadjustedClose() {
    return previousadjustedClose;
  }

  public void setPreviousadjustedClose(double previousadjustedClose) {
    this.previousadjustedClose = previousadjustedClose;
  }

  public double getPercentChange() {
    return percentChange;
  }

  public void setPercentChange(double percentChange) {
    this.percentChange = percentChange;
  }
}
