drop table IF EXISTS INDEX_EOD_QUOTE CASCADE;
CREATE TABLE INDEX_EOD_QUOTE
(
    ID             SERIAL PRIMARY KEY,
    DATE           date        NOT NULL,
    CODE           varchar(50) NOT NULL,
    ADJUSTED_CLOSE double precision,
    VOLUME         double precision,
    unique (DATE, CODE)
);
