package ch.nblotti.securities.firm.repository.eod;

class SharesStatsDTO {
  public long SharesOutstanding;
  public long SharesFloat;
  public float PercentInsiders;
  public float PercentInstitutions;
  public long SharesShort;
  public long SharesShortPriorMonth;
  public float ShortRatio;
  public float ShortPercentOutstanding;
  public float ShortPercentFloat;

  public SharesStatsDTO() {
  }

}
