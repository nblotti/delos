package ch.nblotti.securities.firm.topmovers;

import java.time.LocalDate;

public class FirmTopMoversDTO  {


  private LocalDate date;


  private Integer id;


  private String code;


  private String exchange;

  private String currentExchange;

  private String sector;

  private String industry;

  private String name;

  private String type;


  private Double volume;

  private String isin;

  private String cusip;

  private LocalDate updatedat;


  private Double adjustedClose;

  private Double previousAdjustedClose;

  private Double percentChange;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getVolume() {
    return volume;
  }

  public void setVolume(Double volume) {
    this.volume = volume;
  }

  public Double getPercentChange() {
    return percentChange;
  }

  public void setPercentChange(Double lastMove) {
    this.percentChange = lastMove;
  }

  public String getIsin() {
    return isin;
  }

  public void setIsin(String isin) {
    this.isin = isin;
  }

  public String getCusip() {
    return cusip;
  }

  public void setCusip(String cusip) {
    this.cusip = cusip;
  }

  public LocalDate getUpdatedat() {
    return updatedat;
  }

  public void setUpdatedat(LocalDate updatedat) {
    this.updatedat = updatedat;
  }

  public Double getAdjustedClose() {
    return adjustedClose;
  }

  public void setAdjustedClose(Double adjustedClose) {
    this.adjustedClose = adjustedClose;
  }

  public Double getPreviousAdjustedClose() {
    return previousAdjustedClose;
  }

  public void setPreviousAdjustedClose(Double previousAdjustedClose) {
    this.previousAdjustedClose = previousAdjustedClose;
  }

  public String getCurrentExchange() {
    return currentExchange;
  }

  public void setCurrentExchange(String currentExchange) {
    this.currentExchange = currentExchange;
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
