drop MATERIALIZED VIEW   IF EXISTS FIRM_WEEKLY_QUOTE CASCADE;
CREATE
MATERIALIZED VIEW FIRM_WEEKLY_QUOTE AS
select ROW_NUMBER () OVER () ID,
       fq.EXCHANGE,
       fq.CODE,
       t.ORDINAL            WEEK_NUMBER,
       t.STARTDATE,
       t.ENDDATE,
       t.TYPE,
       percentile_disc(0.5) within group (order by adjusted_close) MEDIAN_ADJUSTED_CLOSE,
        percentile_disc(0.5) within group (order by market_cap) MEDIAN_MARKET_CAP,
    percentile_disc(0.5) within group (order by volume) MEDIAN_VOLUME,
    AVG(adjusted_close) AVG_ADJUSTED_CLOSE,
    AVG(market_cap) AVG_MARKET_CAP,
    AVG(volume) AVG_VOLUME
from firm_eod_quote fq, time t
where fq.date between t.startdate
  and t.enddate
  and
    t.type = 'WEEKS'
group by fq.exchange, t.ordinal, t.startdate, t.enddate, t.type, fq.code
order by code, week_number asc;


drop MATERIALIZED VIEW   IF EXISTS FIRM_MONTHLY_QUOTE CASCADE;
CREATE
MATERIALIZED VIEW FIRM_MONTHLY_QUOTE AS
select ROW_NUMBER () OVER () ID,
       fq.EXCHANGE,
       fq.CODE,
       t.ORDINAL            MONTH_NUMBER,
       t.STARTDATE,
       t.ENDDATE,
       t.TYPE,
       percentile_disc(0.5) within group (order by adjusted_close) MEDIAN_ADJUSTED_CLOSE,
        percentile_disc(0.5) within group (order by market_cap) MEDIAN_MARKET_CAP,
    percentile_disc(0.5) within group (order by volume) MEDIAN_VOLUME,
    AVG(adjusted_close) AVG_ADJUSTED_CLOSE,
    AVG(market_cap) AVG_MARKET_CAP,
    AVG(volume) AVG_VOLUME
from firm_eod_quote fq, time t
where fq.date between t.startdate
  and t.enddate
  and
    t.type = 'MONTHS'
group by fq.exchange, t.ordinal, t.startdate, t.enddate, t.type, fq.code
order by code, month_number asc;
