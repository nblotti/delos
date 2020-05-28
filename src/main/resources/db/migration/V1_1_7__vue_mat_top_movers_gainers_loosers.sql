DROP MATERIALIZED VIEW  IF EXISTS mv_movers;
create materialized view mv_movers as
select * from (
                  select r.*, CASE   WHEN cast(r.previous_adjusted_close as numeric) <1 THEN 0 ELSE ROUND(cast((((r.adjusted_close - r.previous_adjusted_close)/  r.previous_adjusted_close ) * 100)as numeric),2)  END last_move, RANK() OVER (partition by code
                  order by date) rank from (
   with cte AS (
    select * from (
        select *, RANK() OVER (partition by code order by date desc) rank_number from firm_eod_quote where code in (select distinct fi.code from FIRM_EOD_SHARE_STATS fs, firm_eod_info fi where
     fs.shares_outstanding != 0 and fi.type = 'Common Stock' and fs.code = fi.code and fs.exchange = fi.exchange)
    ) s where rank_number = 1 or rank_number = 5 order by code, rank_number desc
   )
   select date, code, exchange, adjusted_close, LAG (adjusted_close,1) over (order by code) previous_adjusted_close , volume from cte
   ) r
 )t
where t.rank = 2;

drop FUNCTION IF EXISTS refresh_fn CASCADE;
drop PROCEDURE IF EXISTS refresh_fn CASCADE;

CREATE FUNCTION refresh_fn() RETURNS integer
LANGUAGE PLPGSQL
AS $$
BEGIN
   REFRESH MATERIALIZED VIEW mv_movers;
   RETURN 1;
END;
$$;


