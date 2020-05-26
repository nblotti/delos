create materialized view mv_movers as
select *
from (
         select r.*, CASE   WHEN cast(r.previous_adjusted_close as numeric) <1 THEN 0 ELSE ROUND(cast((((r.adjusted_close - r.previous_adjusted_close)/  r.previous_adjusted_close ) * 100)as numeric),2)  END last_move, RANK() OVER (partition by code
         order by date) rank from (
   with cte AS (
    select * from (
     select *, RANK() OVER (partition by code order by date desc) rank_number from firm_eod_quote
    ) s where rank_number = 1 or rank_number = 5 order by code, rank_number desc
   )
   select date, code, exchange, adjusted_close, LAG (adjusted_close,1) over (order by code) previous_adjusted_close , volume from cte
   ) r
 )t
where t.rank = 2;

