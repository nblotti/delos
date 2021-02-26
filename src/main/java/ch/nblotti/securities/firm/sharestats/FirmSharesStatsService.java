package ch.nblotti.securities.firm.sharestats;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class FirmSharesStatsService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";

  @Autowired
  protected ModelMapper modelMapper;


  @Autowired
  private FirmSharesStatsRepository firmSharesStatsRepository;


  public List<FirmShareStatsDTO> saveAll(List<FirmShareStatsDTO> entity) {

    List<FirmShareStatsTO> firmQuoteTOS = entity.stream().map(x -> modelMapper.map(x, FirmShareStatsTO.class)).collect(Collectors.toList());

    Iterable<FirmShareStatsTO> saved = firmSharesStatsRepository.saveAll(firmQuoteTOS);

    return StreamSupport.stream(saved.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmShareStatsDTO.class))
      .collect(Collectors.toList());


  }


  @PostConstruct
  public void initFirmEODShareStatsTOMapper() {

    Converter<FirmShareStatsDTO, FirmShareStatsTO> toUppercase = new AbstractConverter<FirmShareStatsDTO, FirmShareStatsTO>() {

      @Override
      protected FirmShareStatsTO convert(FirmShareStatsDTO firmShareStatsDTO) {
        FirmShareStatsTO firmShareStatsTO = new FirmShareStatsTO();
        firmShareStatsTO.setSharesOutstanding(firmShareStatsDTO.getSharesOutstanding());
        firmShareStatsTO.setSharesFloat(firmShareStatsDTO.getSharesFloat());
        firmShareStatsTO.setPercentInsiders(firmShareStatsDTO.getPercentInsiders());
        firmShareStatsTO.setPercentInstitutions(firmShareStatsDTO.getPercentInstitutions());
        firmShareStatsTO.setSharesShort(firmShareStatsDTO.getSharesShort());
        firmShareStatsTO.setSharesShortPriorMonth(firmShareStatsDTO.getSharesShortPriorMonth());
        firmShareStatsTO.setShortRatio(firmShareStatsDTO.getShortRatio());
        firmShareStatsTO.setShortPercentOutstanding(firmShareStatsDTO.getShortPercentOutstanding());
        firmShareStatsTO.setShortPercentFloat(firmShareStatsDTO.getShortPercentFloat());
        firmShareStatsTO.setDate(firmShareStatsDTO.getDate());
        firmShareStatsTO.setCode(firmShareStatsDTO.getCode());
        firmShareStatsTO.setExchange(firmShareStatsDTO.getExchange());

        return firmShareStatsTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmShareStatsDTOMapper() {

    Converter<FirmShareStatsTO, FirmShareStatsDTO> toUppercase = new AbstractConverter<FirmShareStatsTO, FirmShareStatsDTO>() {

      @Override
      protected FirmShareStatsDTO convert(FirmShareStatsTO firmEODShareStatsTO) {
        FirmShareStatsDTO firmShareStatsDTO = new FirmShareStatsDTO();
        firmShareStatsDTO.setSharesOutstanding(firmEODShareStatsTO.getSharesOutstanding());
        firmShareStatsDTO.setSharesFloat(firmEODShareStatsTO.getSharesFloat());
        firmShareStatsDTO.setPercentInsiders(firmEODShareStatsTO.getPercentInsiders());
        firmShareStatsDTO.setPercentInstitutions(firmEODShareStatsTO.getPercentInstitutions());
        firmShareStatsDTO.setSharesShort(firmEODShareStatsTO.getSharesShort());
        firmShareStatsDTO.setSharesShortPriorMonth(firmEODShareStatsTO.getSharesShortPriorMonth());
        firmShareStatsDTO.setShortRatio(firmEODShareStatsTO.getShortRatio());
        firmShareStatsDTO.setShortPercentOutstanding(firmEODShareStatsTO.getShortPercentOutstanding());
        firmShareStatsDTO.setShortPercentFloat(firmEODShareStatsTO.getShortPercentFloat());
        firmShareStatsDTO.setDate(firmEODShareStatsTO.getDate());
        firmShareStatsDTO.setCode(firmEODShareStatsTO.getCode());
        firmShareStatsDTO.setExchange(firmEODShareStatsTO.getExchange());

        return firmShareStatsDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }



  public void deleteByDate(LocalDate localDate) {
    firmSharesStatsRepository.deleteByDateSql(localDate);
  }
}
