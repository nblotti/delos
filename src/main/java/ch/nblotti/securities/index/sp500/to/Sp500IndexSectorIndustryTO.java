package ch.nblotti.securities.index.sp500.to;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SP500_COMPOSITION")
public class Sp500IndexSectorIndustryTO {

  @Id
  @Column(name = "id")

  @SequenceGenerator(initialValue = 2000000, name = "id_gen_sp_500", sequenceName = "sp500_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_sp_500")
  private Long id;

  @Column(name = "codeFirm")
  private String codeFirm;

  @Column(name = "exchange")
  private String exchange;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "sector")
  private String sector;

  @Column(name = "industry")
  private String industry;

  public Sp500IndexSectorIndustryTO() {
  }

  public String getCodeFirm() {
    return codeFirm;
  }

  public void setCodeFirm(String codeFirm) {
    this.codeFirm = codeFirm;
  }

  public String getExchange() {
    return exchange;
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
}
