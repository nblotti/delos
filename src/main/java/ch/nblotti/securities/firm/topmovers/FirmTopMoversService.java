package ch.nblotti.securities.firm.topmovers;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FirmTopMoversService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  FirmTopMoversRepository firmTopMoversRepository;

  public Iterable<FirmTopMoversDTO> findFirst10ByCurrentExchangeOrderByVolumeDesc(String exchange) {

    Iterable<FirmTopMoversTO> firmTopMoversTOS = firmTopMoversRepository.findFirst10ByCurrentExchangeOrderByVolumeDesc(exchange);

    return StreamSupport.stream(firmTopMoversTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmTopMoversDTO.class))
      .collect(Collectors.toList());

  }

  public Iterable<FirmTopMoversDTO> findFirst10ByCurrentExchangeOrderByPercentChangeDesc(String exchange) {
    Iterable<FirmTopMoversTO> firmTopMoversTOS = firmTopMoversRepository.findFirst10ByCurrentExchangeOrderByPercentChangeDesc(exchange);
    return StreamSupport.stream(firmTopMoversTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmTopMoversDTO.class))
      .collect(Collectors.toList());
  }

  public Iterable<FirmTopMoversDTO> findFirst10ByCurrentExchangeOrderByPercentChangeAsc(String exchange) {
    Iterable<FirmTopMoversTO> firmTopMoversTOS = firmTopMoversRepository.findFirst10ByCurrentExchangeOrderByPercentChangeAsc(exchange);
    return StreamSupport.stream(firmTopMoversTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmTopMoversDTO.class))
      .collect(Collectors.toList());
  }

  public FirmTopMoversTO findByCode(String code) {

    FirmTopMoversTO firmTopMoversTO = firmTopMoversRepository.findByCode(code);

    return modelMapper.map(firmTopMoversTO, FirmTopMoversTO.class);


  }


  @PostConstruct
  public void initFirmTopMoversTOMapper() {

    Converter<FirmTopMoversDTO, FirmTopMoversTO> toUppercase = new AbstractConverter<FirmTopMoversDTO, FirmTopMoversTO>() {

      @Override
      protected FirmTopMoversTO convert(FirmTopMoversDTO firmTopMoversDTO) {
        FirmTopMoversTO firmTopMoversTO = new FirmTopMoversTO();
        firmTopMoversTO.setId(firmTopMoversDTO.getId());
        firmTopMoversTO.setDate(firmTopMoversDTO.getDate());
        firmTopMoversTO.setCode(firmTopMoversDTO.getCode());
        firmTopMoversTO.setExchange(firmTopMoversDTO.getExchange());
        firmTopMoversTO.setName(firmTopMoversDTO.getName());
        firmTopMoversTO.setType(firmTopMoversDTO.getType());
        firmTopMoversTO.setVolume(firmTopMoversDTO.getVolume());
        firmTopMoversTO.setPercentChange(firmTopMoversDTO.getPercentChange());
        firmTopMoversTO.setIsin(firmTopMoversDTO.getIsin());
        firmTopMoversTO.setCusip(firmTopMoversDTO.getCusip());
        firmTopMoversTO.setUpdatedat(firmTopMoversDTO.getUpdatedat());
        firmTopMoversTO.setAdjustedClose(firmTopMoversDTO.getAdjustedClose());
        firmTopMoversTO.setPreviousAdjustedClose(firmTopMoversDTO.getPreviousAdjustedClose());
        firmTopMoversTO.setCurrentExchange(firmTopMoversDTO.getCurrentExchange());
        firmTopMoversTO.setSector(firmTopMoversDTO.getSector());
        firmTopMoversTO.setIndustry(firmTopMoversDTO.getIndustry());

        return firmTopMoversTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmTopMoversDTOMapper() {

    Converter<FirmTopMoversTO, FirmTopMoversDTO> toUppercase = new AbstractConverter<FirmTopMoversTO, FirmTopMoversDTO>() {

      @Override
      protected FirmTopMoversDTO convert(FirmTopMoversTO sharesStatsDTO) {
        FirmTopMoversDTO firmTopMoversDTO = new FirmTopMoversDTO();
        firmTopMoversDTO.setId(sharesStatsDTO.getId());
        firmTopMoversDTO.setDate(sharesStatsDTO.getDate());
        firmTopMoversDTO.setCode(sharesStatsDTO.getCode());
        firmTopMoversDTO.setExchange(sharesStatsDTO.getExchange());
        firmTopMoversDTO.setName(sharesStatsDTO.getName());
        firmTopMoversDTO.setType(sharesStatsDTO.getType());
        firmTopMoversDTO.setVolume(sharesStatsDTO.getVolume());
        firmTopMoversDTO.setPercentChange(sharesStatsDTO.getPercentChange());
        firmTopMoversDTO.setIsin(sharesStatsDTO.getIsin());
        firmTopMoversDTO.setCusip(sharesStatsDTO.getCusip());
        firmTopMoversDTO.setUpdatedat(sharesStatsDTO.getUpdatedat());
        firmTopMoversDTO.setAdjustedClose(sharesStatsDTO.getAdjustedClose());
        firmTopMoversDTO.setPreviousAdjustedClose(sharesStatsDTO.getPreviousAdjustedClose());
        firmTopMoversDTO.setCurrentExchange(sharesStatsDTO.getCurrentExchange());
        firmTopMoversDTO.setSector(sharesStatsDTO.getSector());
        firmTopMoversDTO.setIndustry(sharesStatsDTO.getIndustry());

        return firmTopMoversDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

}
