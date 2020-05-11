drop table IF EXISTS SP500_COMPOSITION CASCADE;
CREATE TABLE SP500_COMPOSITION
(

    ID        SERIAL PRIMARY KEY,
    DATE      date        NOT NULL,
    CODE_FIRM varchar(50) NOT NULL,
    EXCHANGE  varchar(50) NOT NULL,
    SECTOR    varchar(50) NOT NULL,
    INDUSTRY  varchar(50) NOT NULL

);

drop index IF EXISTS sp500_composition_idx CASCADE;
CREATE UNIQUE INDEX sp500_composition_idx ON sp500_composition (date, code_firm, exchange);






