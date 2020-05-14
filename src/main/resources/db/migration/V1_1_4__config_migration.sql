drop table IF EXISTS CONFIG CASCADE;
CREATE TABLE CONFIG
(
    ID    SERIAL PRIMARY KEY,
    CODE  varchar(50) NOT NULL,
    TYPE  varchar(50) NOT NULL,
    KEY  varchar(50) NOT NULL,
    VALUE text NOT NULL,
    unique (CODE, TYPE)
);

drop index IF EXISTS config_idx CASCADE;
CREATE UNIQUE INDEX config_idx ON CONFIG (code, type,key);
