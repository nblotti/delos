drop table IF EXISTS firm CASCADE;
CREATE TABLE firm
(
    ID       SERIAL PRIMARY KEY,
    CODE     varchar(50) NOT NULL,
    NAME     varchar(50) NOT NULL,
    EXCHANGE varchar(50) NOT NULL,
    SECTOR   varchar(50) NOT NULL,
    INDUSTRY varchar(50) NOT NULL,
    unique (exchange, code)
);

drop index IF EXISTS firm_idx CASCADE;
CREATE UNIQUE INDEX firm_idx ON firm (code, EXCHANGE);

drop index IF EXISTS firm_sector_idx CASCADE;
CREATE UNIQUE INDEX firm_sector_idx ON firm (sector, industry, code);

drop table IF EXISTS market_cap CASCADE;
CREATE TABLE market_cap
(
    ID         SERIAL PRIMARY KEY,
    CODE       varchar(50)      NOT NULL,
    EXCHANGE   varchar(50)      NOT NULL,
    DATE       date             NOT NULL,
    MARKET_CAP double precision NOT NULL,
    unique (code, exchange, date)
);


drop index IF EXISTS market_cap_idx CASCADE;
CREATE UNIQUE INDEX market_cap_idx ON market_cap (EXCHANGE, code, DATE);
