package ch.nblotti.securities.index.sp500;

public enum LOADER_STATES {
  READY,
  GET_DATES,
  LOAD_NYSE,
  LOAD_NASDAQ,
  SAVE_FIRM,
  END_OF_MONTH,
  ERROR,
  DONE;
}
