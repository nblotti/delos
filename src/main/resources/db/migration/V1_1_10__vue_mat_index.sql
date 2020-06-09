DROP
MATERIALIZED VIEW  IF EXISTS mv_movers_index;

create
materialized view mv_movers_index as
select  ROW_NUMBER() OVER (order by code) row_number, date,code, volume, adjusted_close, previous_adjusted_close, adjusted_close - previous_adjusted_close last_move
from (
    select *, RANK() OVER ( partition by code
    order by date desc) rank from (
    with cte AS (
    select * from ( select *, RANK() OVER (partition by code order by date desc) rank_number from index_eod_quote  ) r
    where rank_number = 1 or rank_number = 5 order by code, rank_number desc
    )
    select date, code,  adjusted_close, LAG (adjusted_close,1) over (order by code) previous_adjusted_close , volume from cte
    )  t
    ) u
where rank = 1
order by code, date desc;

drop
FUNCTION IF EXISTS refresh_fn CASCADE;

CREATE
FUNCTION refresh_fn() RETURNS integer
LANGUAGE PLPGSQL
AS $$
BEGIN
 REFRESH MATERIALIZED VIEW mv_movers;
 REFRESH MATERIALIZED VIEW mv_movers_volume;
 REFRESH MATERIALIZED VIEW mv_movers_index;
   RETURN 1;
END;
$$;

