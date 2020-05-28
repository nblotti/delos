package ch.nblotti.securities.firm.to;

import org.springframework.data.annotation.TypeAlias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "mv_movers_volume")
@Entity
public class TopMoversVolumeTO implements Serializable {


  @Id
  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;


  @Column(name = "exchange")
  private String exchange;

  @Column(name = "volume")
  private double volume;

  @Column(name = "updated_at")
  private LocalDate updatedDate;

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

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

  public LocalDate getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(LocalDate updatedDate) {
    this.updatedDate = updatedDate;
  }
}
