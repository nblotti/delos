package ch.nblotti.securities.firm.dto;

public class ValuationDTO {

  public Float TrailingPE;
  public Float ForwardPE;
  public Float PriceSalesTTM;
  public Float PriceBookMRQ;
  public Float EnterpriseValueRevenue;
  public Float EnterpriseValueEbitda;

  public ValuationDTO() {

  }

  public Float getTrailingPE() {
    return TrailingPE;
  }

  public Float getForwardPE() {
    return ForwardPE;
  }

  public Float getPriceSalesTTM() {
    return PriceSalesTTM;
  }

  public Float getPriceBookMRQ() {
    return PriceBookMRQ;
  }

  public Float getEnterpriseValueRevenue() {
    return EnterpriseValueRevenue;
  }

  public Float getEnterpriseValueEbitda() {
    return EnterpriseValueEbitda;
  }
}
