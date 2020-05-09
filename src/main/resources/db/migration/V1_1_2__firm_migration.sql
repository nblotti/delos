drop table IF EXISTS FIRM_EOD_QUOTE CASCADE;
CREATE TABLE FIRM_EOD_QUOTE
(
    ID             SERIAL PRIMARY KEY,
    DATE           date        NOT NULL,
    CODE           varchar(50) NOT NULL,
    EXCHANGE       varchar(50) NOT NULL,
    NAME           varchar(200) NOT NULL,
    MARKET_CAP     double precision,
    ADJUSTED_CLOSE double precision,
    VOLUME         double precision,
    unique (DATE, CODE, EXCHANGE)
);

drop table IF EXISTS FIRM_EOD_SHARE_STATS CASCADE;
CREATE TABLE FIRM_EOD_SHARE_STATS
(
    ID                        SERIAL PRIMARY KEY,
    DATE                      date        NOT NULL,
    CODE                      varchar(50) NOT NULL,
    EXCHANGE                  varchar(50) NOT NULL,
    SHARES_OUTSTANDING        bigint,
    SHARES_FLOAT              bigint,
    PERCENT_INSIDERS          double precision,
    PERCENT_INSTITUTIONS      double precision,
    SHARES_SHORT              bigint,
    SHARES_SHORT_PRIOR_MONTH  bigint,
    SHORT_RATIO               double precision,
    SHORT_PERCENT_OUTSTANDING double precision,
    SHORT_PERCENT_FLOAT       double precision,
    unique (DATE, CODE, EXCHANGE)
);


drop table IF EXISTS FIRM_EOD_HIGHLIGHTS CASCADE;
CREATE TABLE FIRM_HIGHLIGHTS
(
    ID                           SERIAL PRIMARY KEY,
    DATE                         date        NOT NULL,
    CODE                         varchar(50) NOT NULL,
    EXCHANGE                     varchar(50) NOT NULL,
    MARKET_CAPITALIZATION        double precision,
    MARKET_CAPITALIZATION_MLN    double precision,
    EBITDA                       double precision,
    PE_RATIO                     double precision,
    PEG_RATIO                    double precision,
    WALL_STREET_TARGET_PRICE     double precision,
    BOOK_VALUE                   double precision,
    DIVIDEND_SHARE               double precision,
    DIVIDEND_YIELD               double precision,
    EARNING_SHARE                double precision,
    EPS_ESTIMATE_CURRENT_YEAR    double precision,
    EPS_ESTIMATE_NEXT_YEAR       double precision,
    EPS_ESTIMATE_NEXT_QUARTER    double precision,
    EPS_ESTIMATE_CURRENT_QUARTER double precision,
    MOST_RECENT_QUARTER          varchar(50),
    PROFIT_MARGIN                double precision,
    OPERTING_MARGIN_TTM          double precision,
    RETURN_ON_ASSETS_TTM         double precision,
    RETURN_ON_EQUITY_TTM         double precision,
    REVENUE_TTM                  double precision,
    REVENUE_PER_SHARE_TTM        double precision,
    QUARTER_REVENUE_GROWTH_YOY   double precision,
    GROSS_PROFIT_TTM             double precision,
    DILUTED_EPS_TTM              double precision,
    QUARTERLY_EARNING_GROWTH_YOY double precision,
    unique (date, code, exchange)
);



drop table IF EXISTS FIRM_EOD_VALUATION CASCADE;
CREATE TABLE FIRM_EOD_VALUATION
(
    ID                       SERIAL PRIMARY KEY,
    DATE                     date        NOT NULL,
    CODE                     varchar(50) NOT NULL,
    EXCHANGE                 varchar(50) NOT NULL,
    TRAILING_PE              double precision,
    FORWARD_PE               double precision,
    PRICE_SALES_TTM          double precision,
    PRICE_BOOK_MRQ           double precision,
    ENTERPRISE_VALUE_REVENUE double precision,
    ENTERPRISE_VALUE_EBITDA   double precision,
    unique (DATE, CODE, EXCHANGE)
);

