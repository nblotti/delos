package ch.nblotti.securities.firm.valuation;


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
public class FirmValuationService {

  private static final Logger logger = Logger.getLogger("FirmValuationService");


  @Autowired
  private FirmValuationRepository firmValuationRepository;


  @Autowired
  protected ModelMapper modelMapper;

  public FirmValuationDTO save(FirmValuationDTO entity) {


    FirmValuationTO firmQuoteTOS = modelMapper.map(entity, FirmValuationTO.class);
    FirmValuationTO saved = firmValuationRepository.save(firmQuoteTOS);

    return modelMapper.map(saved, FirmValuationDTO.class);


  }


  @PostConstruct
  public void initFirmValuationDTOMapper() {

    Converter<FirmValuationDTO, FirmValuationTO> toUppercase = new AbstractConverter<FirmValuationDTO, FirmValuationTO>() {

      @Override
      protected FirmValuationTO convert(FirmValuationDTO firmValuationDTO) {
        FirmValuationTO firmValuationTO = new FirmValuationTO();
        firmValuationTO.setDate(firmValuationDTO.getDate());
        firmValuationTO.setCode(firmValuationDTO.getCode());
        firmValuationTO.setExchange(firmValuationDTO.getExchange());
        firmValuationTO.setTrailingPE(firmValuationDTO.getTrailingPE());
        firmValuationTO.setForwardPE(firmValuationDTO.getForwardPE());
        firmValuationTO.setPriceSalesTTM(firmValuationDTO.getPriceSalesTTM());
        firmValuationTO.setPriceBookMRQ(firmValuationDTO.getPriceBookMRQ());
        firmValuationTO.setEnterpriseValueRevenue(firmValuationDTO.getEnterpriseValueRevenue());
        firmValuationTO.setEnterpriseValueEbitda(firmValuationDTO.getEnterpriseValueEbitda());
        return firmValuationTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmValuationTOMapper() {

    Converter<FirmValuationTO, FirmValuationDTO> toUppercase = new AbstractConverter<FirmValuationTO, FirmValuationDTO>() {

      @Override
      protected FirmValuationDTO convert(FirmValuationTO firmValuationTO) {
        FirmValuationDTO firmValuationDTO = new FirmValuationDTO();
        firmValuationDTO.setDate(firmValuationTO.getDate());
        firmValuationDTO.setCode(firmValuationTO.getCode());
        firmValuationDTO.setExchange(firmValuationTO.getExchange());
        firmValuationDTO.setTrailingPE(firmValuationTO.getTrailingPE());
        firmValuationDTO.setForwardPE(firmValuationTO.getForwardPE());
        firmValuationDTO.setPriceSalesTTM(firmValuationTO.getPriceSalesTTM());
        firmValuationDTO.setPriceBookMRQ(firmValuationTO.getPriceBookMRQ());
        firmValuationDTO.setEnterpriseValueRevenue(firmValuationTO.getEnterpriseValueRevenue());
        firmValuationDTO.setEnterpriseValueEbitda(firmValuationTO.getEnterpriseValueEbitda());
        return firmValuationDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  public void deleteByDate(LocalDate localDate) {
    firmValuationRepository.deleteByDateSql(localDate);
  }
}
