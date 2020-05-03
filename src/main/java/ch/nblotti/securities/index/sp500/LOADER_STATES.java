package ch.nblotti.securities.index.sp500;

public enum LOADER_STATES {
  READY,
  CHECK_END_OF_MONTH,
  DETERMINING_DATE,
  FIRM,
  MARKET_CAP,
  PRICE,
  ERROR,
  DONE;
}
