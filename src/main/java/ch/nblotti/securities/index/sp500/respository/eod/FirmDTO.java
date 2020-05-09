package ch.nblotti.securities.index.sp500.respository.eod;

class FirmDTO {

  private Integer id;

  private String code;

  private String exchange;

  private String name;

  private String sector;

  private String industry;

  public FirmDTO() {
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


