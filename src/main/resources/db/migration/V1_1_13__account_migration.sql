CREATE TABLE IF NOT EXISTS account
(

    ID            SERIAL PRIMARY KEY,
    OPENING_DATE  timestamp   NOT NULL,
    PERF_CURRENCY varchar(10) NOT NULL
);





