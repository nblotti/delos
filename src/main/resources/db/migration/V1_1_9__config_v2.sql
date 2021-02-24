drop table IF EXISTS CONFIG CASCADE;

drop index IF EXISTS config_code_type_key CASCADE;
drop index IF EXISTS config_idx CASCADE;

CREATE TABLE CONFIG
(
    ID    SERIAL PRIMARY KEY,
    CODE  varchar(50) NOT NULL,
    TYPE  varchar(50) NOT NULL,
    VALUE text NOT NULL
);


