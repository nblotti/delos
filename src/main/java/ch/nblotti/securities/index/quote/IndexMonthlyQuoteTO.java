package ch.nblotti.securities.index.quote;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "INDEX_MONTHLY_QUOTE")
class IndexMonthlyQuoteTO {


  @Id
  @SequenceGenerator(initialValue = 2000000, name = "id_gen_index_monthly_quote", sequenceName = "index_eod_quote_monthly_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_gen_index_monthly_quote")
  private Integer id;

  @Column(name = "EXCHANGE")
  String exchangeShortName;

  @Column(name = "GIC_SECTOR")
  String gicSector;

  @Column(name = "MONTH_NUMBER")
  int month_number;

  @Column(name = "STARTDATE")
  LocalDate startDate;

  @Column(name = "ENDDATE")
  LocalDate endDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE")
  private ChronoUnit type;

  @Column(name = "MEDIAN_MARKET_CAP")
  long medianMarketCapitalization;

  @Column(name = "MEDIAN_ADJUSTED_CLOSE")
  float medianAdjustedClose;

  @Column(name = "MEDIAN_VOLUME")
  long medianVolume;

  @Column(name = "AVG_MARKET_CAP")
  long averageMarketCapitalization;

  @Column(name = "AVG_ADJUSTED_CLOSE")
  float averageAdjustedClose;

  @Column(name = "AVG_VOLUME")
  long averageVolume;



}
