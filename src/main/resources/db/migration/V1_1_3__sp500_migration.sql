drop view IF EXISTS SP_500_BY_SECTOR_DATE;
CREATE MATERIALIZED VIEW SP_500_BY_SECTOR_DATE AS
(
SELECT row_number() OVER (ORDER BY subquery.sector,subquery.date) AS id,
       subquery.sector,
       subquery.date,
       subquery.market_cap
FROM
  (
select f.sector, m.date, sum(market_cap) market_cap
from market_cap m
         right outer join firm f
                          on f.code = m.code
group by m.date, f.sector
order by f.sector, m.date asc
  ) subquery);

drop view IF EXISTS SP_500_BY_SECTOR_INDUSTRY_DATE;
CREATE MATERIALIZED VIEW SP_500_BY_SECTOR_INDUSTRY_DATE AS
(
SELECT row_number() OVER (ORDER BY subquery.sector, subquery.industry, subquery.date) AS id,
       subquery.sector,
       subquery.industry,
       subquery.date,
       subquery.market_cap
FROM
  (
select f.sector, f.industry, m.date, sum(market_cap) market_cap
from market_cap m
         right outer join firm f
                          on f.exchange = m.exchange and f.code = m.code
group by f.sector, f.industry, m.date
order by f.sector, f.industry, m.date asc
  ) subquery);

