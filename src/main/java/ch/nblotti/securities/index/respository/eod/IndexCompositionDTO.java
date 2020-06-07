package ch.nblotti.securities.index.respository.eod;

class IndexCompositionDTO {


  String Code;

  String Exchange;

  String Name;

  String Sector;

  String Industry;

  public IndexCompositionDTO() {
  }

  public String getCode() {
    return Code;
  }

  public void setCode(String code) {
    Code = code;
  }

  public String getExchange() {
    return Exchange;
  }

  public void setExchange(String exchange) {
    Exchange = exchange;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getSector() {
    return Sector;
  }

  public void setSector(String sector) {
    Sector = sector;
  }

  public String getIndustry() {
    return Industry;
  }

  public void setIndustry(String industry) {
    Industry = industry;
  }
}


