drop view IF EXISTS sp_sector_repartition CASCADE;
CREATE view  sp_sector_repartition as
select ROW_NUMBER () OVER (ORDER BY date asc)
id,
date,
       round("Real Estate", 2)            real_estate,
       round("Real Estate" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_real_estate,
       round("Healthcare", 2)             healthcare,
       round("Healthcare" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_healthcare,
       round("Basic Materials", 2)        basic_materials,
       round("Basic Materials" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_basic_materials,
       round("Industrials", 2)            industrials,
       round("Industrials" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_industrials,
       round("Consumer Cyclical", 2)      consumer_cyclical,
       round("Consumer Cyclical" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_consumer_cyclical,
       round("Energy", 2)                 energy,
       round("Energy" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_energy,
       round("Utilities", 2)              utilities,
       round("Utilities" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_utilities,
       round("Technology", 2)             technology,
       round("Technology" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_technology,
       round("Consumer Defensive", 2)     consumer_defensive,
       round("Consumer Defensive" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_consumer_defensive,
       round("Financial Services", 2)     financial_services,
       round("Financial Services" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_financial_services,
       round("Communication Services", 2) communication_services,
       round("Communication Services" /
             ("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
              "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services") *
             100, 2)                      percent_communication_services,
       round("Real Estate" + "Healthcare" + "Basic Materials" + "Industrials" + "Consumer Cyclical" + "Energy" +
             "Utilities" + "Technology" + "Consumer Defensive" + "Financial Services" + "Communication Services",
             2) as                        total
FROM crosstab(
             'select  s.date, s.sector, s.adjusted_close::numeric from ( select ic.date, ic.sector ,ic.industry, sum(fq.adjusted_close) adjusted_close from index_composition ic, firm_eod_quote fq where ic.code_firm = fq.code and ic.date = fq.date  group by ic.date, ic.sector, ic.industry order by ic.date asc) s'
         ) AS ct (
                  "date" date,
                  "Real Estate" numeric,
                  "Healthcare" numeric,
                  "Basic Materials" numeric,
                  "Industrials" numeric,
                  "Consumer Cyclical" numeric,
                  "Energy" numeric,
                  "Utilities" numeric,
                  "Technology" numeric,
                  "Consumer Defensive" numeric,
                  "Financial Services" numeric,
                  "Communication Services" numeric
    );


