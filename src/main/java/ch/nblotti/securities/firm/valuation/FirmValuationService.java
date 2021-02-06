package ch.nblotti.securities.firm.valuation;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class FirmValuationService {

  private static final Logger logger = Logger.getLogger("FirmValuationService");


  @Autowired
  private EODFirmValuationRepository EODFirmValuationRepository;

  @Autowired
  private FirmValuationRepository firmValuationRepository;


  @Autowired
  protected ModelMapper modelMapper;


  public Optional<FirmValuationDTO> getValuationByDateAndFirm(LocalDate runDate, String exchange, String symbol) {
    Optional<EODValuationDTO> eODValuationDTO = EODFirmValuationRepository.getValuationByDateAndFirm(runDate, exchange, symbol);

    if (!eODValuationDTO.isPresent())
      return Optional.empty();

    FirmValuationDTO fVpost = modelMapper.map(eODValuationDTO, FirmValuationDTO.class);
    fVpost.setExchange(exchange);
    fVpost.setDate(runDate);
    fVpost.setCode(symbol);
    return Optional.of(fVpost);

  }


  @PostConstruct
  public void initValuationMapper() {

    Converter<EODValuationDTO, FirmValuationDTO> toUppercase = new AbstractConverter<EODValuationDTO, FirmValuationDTO>() {

      @Override
      protected FirmValuationDTO convert(EODValuationDTO valuationDTO) {
        FirmValuationDTO firmEODValuationTO = new FirmValuationDTO();
        firmEODValuationTO.setForwardPE(valuationDTO.getForwardPE());
        firmEODValuationTO.setTrailingPE(valuationDTO.getTrailingPE());
        firmEODValuationTO.setPriceSalesTTM(valuationDTO.getPriceSalesTTM());
        firmEODValuationTO.setPriceBookMRQ(valuationDTO.getPriceBookMRQ());
        firmEODValuationTO.setEnterpriseValueRevenue(valuationDTO.getEnterpriseValueRevenue());
        firmEODValuationTO.setEnterpriseValueEbitda(valuationDTO.getEnterpriseValueEbitda());
        return firmEODValuationTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }
}
