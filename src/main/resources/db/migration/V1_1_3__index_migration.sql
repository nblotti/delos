drop index IF EXISTS firm_eod_quote_idx CASCADE;
CREATE UNIQUE INDEX firm_eod_quote_idx ON FIRM_EOD_QUOTE (date, code, exchange);


