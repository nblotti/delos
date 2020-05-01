package ch.nblotti.securities.firm;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "market_cap")
class MarketCapDto {


  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_market_cap", sequenceName = "market_cap_id_seq",allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_gen_market_cap")
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "exchange")
  private String exchange;

  private LocalDate date;

  @JsonProperty("v1")
  private double marketCap;


  public MarketCapDto() {

  }

  public MarketCapDto(LocalDate date, double marketCap) {
    this.date = date;
    this.marketCap = marketCap;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public double getMarketCap() {
    return marketCap;
  }

  public void setMarketCap(double marketCap) {
    this.marketCap = marketCap;
  }
}
