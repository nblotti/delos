drop table  IF  EXISTS INDEX_COMPOSITION CASCADE;
CREATE TABLE  INDEX_COMPOSITION
(

    ID        SERIAL PRIMARY KEY,
    DATE      date        NOT NULL,
    INDEX     varchar(50) NOT NULL,
    CODE_FIRM varchar(50) NOT NULL,
    EXCHANGE  varchar(50) NOT NULL
);

drop index  IF  EXISTS index_composition_idx CASCADE;
CREATE UNIQUE INDEX index_composition_idx ON index_composition (date,index,code_firm,exchange);






