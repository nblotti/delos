ALTER TABLE sp500_composition    RENAME TO index_composition;
update index_composition set exchange = 'GSPC';

drop index IF EXISTS sp500_composition_idx;
CREATE UNIQUE INDEX index_composition_idx ON index_composition (date, code_firm, exchange,sector, industry);
