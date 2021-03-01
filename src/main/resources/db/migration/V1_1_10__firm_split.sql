drop table IF EXISTS FIRM_SPLIT CASCADE;


CREATE TABLE FIRM_SPLIT
(
    ID       SERIAL PRIMARY KEY,
    DATE     date        NOT NULL,
    CODE     varchar(50) NOT NULL,
    EXCHANGE varchar(50) NOT NULL,
    SPLIT    varchar(50) NOT NULL,
    RETRY    bigint      NOT NULL,
    unique (DATE, CODE, EXCHANGE)
);


