package ch.nblotti.securities.index.quote;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "index_eod_quote")
class IndexQuoteTO {


  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_index_quote", sequenceName = "index_eod_quote_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_index_quote")
  private Integer id;

  @Column(name = "code")
  String code;

  @Column(name = "date")
  LocalDate date;

  @Column(name = "open")
  float open;

  @Column(name = "high")
  float high;

  @Column(name = "low")
  float low;

  @Column(name = "close")
  float close;

  @Column(name = "adjusted_close")
  float adjustedClose;

  @Column(name = "volume")
  long volume;

  public IndexQuoteTO() {

  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public float getAdjustedClose() {
    return adjustedClose;
  }

  public void setAdjustedClose(float adjustedClose) {
    this.adjustedClose = adjustedClose;
  }

  public long getVolume() {
    return volume;
  }

  public void setVolume(long volume) {
    this.volume = volume;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public float getOpen() {
    return open;
  }

  public void setOpen(float open) {
    this.open = open;
  }

  public float getHigh() {
    return high;
  }

  public void setHigh(float high) {
    this.high = high;
  }

  public float getLow() {
    return low;
  }

  public void setLow(float low) {
    this.low = low;
  }

  public float getClose() {
    return close;
  }

  public void setClose(float close) {
    this.close = close;
  }
}
