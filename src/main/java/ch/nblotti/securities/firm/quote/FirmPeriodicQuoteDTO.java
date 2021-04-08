package ch.nblotti.securities.firm.quote;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
public class FirmPeriodicQuoteDTO {


  private Integer id;

  private String exchangeShortName;

  private String code;

  private int ordinal;

  private LocalDate startdate;

  private LocalDate enddate;

  private ChronoUnit type;

  private long medianMarketCapitalization;

  private float medianAdjustedClose;

  private long medianVolume;

  private long averageMarketCapitalization;

  private float averageAdjustedClose;

  private long averageVolume;

}
