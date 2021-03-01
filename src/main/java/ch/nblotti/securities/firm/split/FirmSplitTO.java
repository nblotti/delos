package ch.nblotti.securities.firm.split;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "firm_split")
class FirmSplitTO {


  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_firm_split", sequenceName = "firm_split_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_firm_split")
  private Integer id;

  @Column(name = "code")
  String code;

  @Column(name = "date")
  LocalDate date;

  @Column(name = "exchange")
  String exchange;

  @Column(name = "split")
  String split;

  @Column(name = "retry")
  long retry;


  public FirmSplitTO() {

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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }


  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public String getSplit() {
    return split;
  }

  public void setSplit(String split) {
    this.split = split;
  }

  public long getRetry() {
    return retry;
  }

  public void setRetry(long retry) {
    this.retry = retry;
  }
}
