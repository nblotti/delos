package ch.nblotti.securities.index.sp500;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SP_500_BY_SECTOR_INDUSTRY_DATE")
public class Sp500IndexSectorIndustryPO {

  @Id
  @Column(name = "id")

  @SequenceGenerator(initialValue = 2000000, name = "id_gen_sp_500", sequenceName = "sp500_id_seq",allocationSize=1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_gen_sp_500")
  private Long id;

  @Column(name = "sector")
  private String sector;
  @Column(name = "industry")
  private String industry;
  @Column(name = "date")
  private LocalDate date;
  @Column(name = "market_cap")
  private double market_cap;

  public Sp500IndexSectorIndustryPO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public double getMarket_cap() {
    return market_cap;
  }

  public void setMarket_cap(double market_cap) {
    this.market_cap = market_cap;
  }
}
