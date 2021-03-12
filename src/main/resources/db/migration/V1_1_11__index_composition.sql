drop table if exists INDEX_COMPOSITION;

drop index IF EXISTS index_composition_idx;
drop index IF EXISTS sp500_composition_pkey;

CREATE TABLE INDEX_COMPOSITION
(

    ID            SERIAL PRIMARY KEY,
    START_DATE    date        NOT NULL,
    END_DATE      date        NOT NULL,
    CODE          varchar(50) NOT NULL,
    NAME          varchar(50) NOT NULL,
    IS_ACTIVE_NOW BOOLEAN     NOT NULL,
    IS_DELISTED   varchar(50) NOT NULL

);
