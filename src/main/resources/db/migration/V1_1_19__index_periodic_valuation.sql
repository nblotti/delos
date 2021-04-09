drop MATERIALIZED VIEW   IF EXISTS INDEX_WEEKLY_QUOTE CASCADE;
CREATE
MATERIALIZED VIEW INDEX_WEEKLY_QUOTE AS
select  ROW_NUMBER () OVER () ID, fq.EXCHANGE, fi.GIC_SECTOR , t.ordinal WEEK_NUMBER ,t.STARTDATE, t.ENDDATE, t.TYPE,
        percentile_disc(0.5) within group (order by adjusted_close) MEDIAN_ADJUSTED_CLOSE,
        percentile_disc(0.5) within group (order by market_cap) MEDIAN_MARKET_CAP,
    percentile_disc(0.5) within group (order by volume) MEDIAN_VOLUME,
    AVG(adjusted_close) AVG_ADJUSTED_CLOSE,
    AVG(market_cap) AVG_MARKET_CAP,
    AVG(volume) AVG_VOLUME
from index_composition ic, time t, firm_eod_quote fq, firm_eod_info fi
where fq.date between t.startdate and t.enddate
  and fq.code = fi.code
  and fq.date = fi.date
  and fq.date between ic.start_date and ic.end_date
  and fq.code = ic.code
  and    t.type = 'WEEKS'
group by fq.exchange, t.ordinal, t.startdate, t.enddate, t.type, fi.gic_sector
order by fi.gic_sector;


drop MATERIALIZED VIEW   IF EXISTS INDEX_MONTHLY_QUOTE CASCADE;
CREATE
MATERIALIZED VIEW INDEX_MONTHLY_QUOTE AS
select  ROW_NUMBER () OVER () ID, fq.exchange, fi.gic_sector , t.ordinal month_number ,t.startdate, t.enddate, t.type,
        percentile_disc(0.5) within group (order by adjusted_close) MEDIAN_ADJUSTED_CLOSE,
        percentile_disc(0.5) within group (order by market_cap) MEDIAN_MARKET_CAP,
    percentile_disc(0.5) within group (order by volume) MEDIAN_VOLUME,
    AVG(adjusted_close) AVG_ADJUSTED_CLOSE,
    AVG(market_cap) AVG_MARKET_CAP,
    AVG(volume) AVG_VOLUME
from index_composition ic, time t, firm_eod_quote fq, firm_eod_info fi
where fq.date between t.startdate and t.enddate
  and fq.code = fi.code
  and fq.date = fi.date
  and fq.date between ic.start_date and ic.end_date
  and fq.code = ic.code
  and    t.type = 'MONTHS'
group by fq.exchange, t.ordinal, t.startdate, t.enddate, t.type, fi.gic_sector
order by fi.gic_sector;
