CREATE TABLE TIME
(
    ID        SERIAL PRIMARY KEY,
    STARTDATE date        NOT NULL,
    ENDDATE   date        NOT NULL,
    TYPE      varchar(50) NOT NULL,
    ORDINAL   int         NOT NULL
);

CREATE TABLE DAYOFF
(
    ID       SERIAL PRIMARY KEY,
    CODE     varchar(50) NOT NULL,
    NAME     varchar(50) NOT NULL,
    MICS     varchar(50) NOT NULL,
    COUNTRY  varchar(50) NOT NULL,
    CURRENCY varchar(50) NOT NULL,
    TIMEZONE varchar(50) NOT NULL,
    DATE     date        NOT NULL,
    HOLIDAY  varchar(50) NOT NULL

);







