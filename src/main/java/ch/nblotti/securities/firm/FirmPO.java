package ch.nblotti.securities.firm;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "firm")
@Component
public class FirmPO {

  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_firm", sequenceName = "firm_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_firm")
  private Integer id;

  @Column(name = "code")
  private String code;

  @Column(name = "exchange")
  private String exchange;

  @NotNull
  @JsonProperty("Name")
  private String name;

  @NotNull
  @JsonProperty("Sector")
  private String sector;

  @NotNull
  @JsonProperty("Industry")
  private String industry;

  public FirmPO() {
  }


  public FirmPO(String code, String name, String exchange, String sector, String industry) {
    this.code = code;
    this.exchange = exchange;
    this.name = name;
    this.sector = sector;
    this.industry = industry;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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


  public String getCode() {
    return code;
  }

  public String getExchange() {
    return exchange;
  }


}


