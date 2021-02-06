package ch.nblotti.securities.firm.infos;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FirmInfoService {

  private static final Logger logger = Logger.getLogger("FirmService");

  public static final String FIRMS = "firms";
  public static final String FIRM_MAP = "firmsMap";


  @Autowired
  EODFirmInfoRepository EODFirmInfoRepository;

  @Autowired
  FirmInfoRepository firmInfoRepository;


  @Autowired
  protected ModelMapper modelMapper;



  public Optional<FirmInfoDTO> getInfosByDateAndExchangeAndFirm(LocalDate runDate, String exchange, String symbol) {

    Optional<EODFirmInfosDTO> firmInfosDTO = EODFirmInfoRepository.getInfosByDateAndExchangeAndFirm(runDate, exchange, symbol);

    if (!firmInfosDTO.isPresent())
      return Optional.empty();

    FirmInfoDTO fIpost = modelMapper.map(firmInfosDTO, FirmInfoDTO.class);
    fIpost.setExchange(exchange);
    fIpost.setDate(runDate);
    fIpost.setCode(symbol);

    return Optional.of(fIpost);


  }

  public FirmInfoDTO findTopByCodeOrderByDate(String code) {

    FirmInfoTO firmHighlightsDTO = firmInfoRepository.findTopByCodeOrderByDate(code);
    return modelMapper.map(firmHighlightsDTO, FirmInfoDTO.class);

  }

  public Iterable<FirmInfoDTO> findAll(Sort sort) {

    Iterable<FirmInfoTO> firmHighlightsDTOS = firmInfoRepository.findAll(sort);

    return StreamSupport.stream(firmHighlightsDTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, FirmInfoDTO.class))
      .collect(Collectors.toList());

  }

  public FirmInfoDTO save(FirmInfoDTO entity) {

    FirmInfoTO firmInfoTO = modelMapper.map(entity, FirmInfoTO.class);

    FirmInfoTO saved = firmInfoRepository.save(firmInfoTO);

    return modelMapper.map(saved, FirmInfoDTO.class);

  }

  @PostConstruct
  public void initFirmEODInfoMapper() {

    Converter<FirmInfoDTO, FirmInfoTO> toUppercase = new AbstractConverter<FirmInfoDTO, FirmInfoTO>() {

      @Override
      protected FirmInfoTO convert(FirmInfoDTO firmInfoDTO) {
        FirmInfoTO firmInfoTO = new FirmInfoTO();
        firmInfoTO.setCode(firmInfoDTO.getCode());
        firmInfoTO.setName(firmInfoDTO.getName());
        firmInfoTO.setType(firmInfoDTO.getType());
        firmInfoTO.setExchange(firmInfoDTO.getExchange());
        firmInfoTO.setCurrencyName(firmInfoDTO.getCurrencyName());
        firmInfoTO.setCurrencySymbol(firmInfoDTO.getCurrencySymbol());
        firmInfoTO.setCountryISO(firmInfoDTO.getCountryISO());
        firmInfoTO.setIsin(firmInfoDTO.getIsin());
        firmInfoTO.setcCusip(firmInfoDTO.getCusip());
        firmInfoTO.setcCik(firmInfoDTO.getCik());
        firmInfoTO.setEmployerIdNumber(firmInfoDTO.getEmployerIdNumber());
        firmInfoTO.setFiscalYearEnd(firmInfoDTO.getFiscalYearEnd());
        firmInfoTO.setiPODate(firmInfoDTO.getiPODate());
        firmInfoTO.setInternationalDomestic(firmInfoDTO.getInternationalDomestic());
        firmInfoTO.setSector(firmInfoDTO.getSector());

        firmInfoTO.setIndustry(firmInfoDTO.getIndustry());
        firmInfoTO.setGicSector(firmInfoDTO.getGicSector());
        firmInfoTO.setGicGroup(firmInfoDTO.getGicGroup());
        firmInfoTO.setGicIndustry(firmInfoDTO.getGicIndustry());
        firmInfoTO.setGicSubIndustry(firmInfoDTO.getGicSubIndustry());
        firmInfoTO.setDescription(firmInfoDTO.getDescription());
        firmInfoTO.setAddress(firmInfoDTO.getAddress());

        firmInfoTO.setPhone(firmInfoDTO.getPhone());
        firmInfoTO.setWebURL(firmInfoDTO.getWebURL());
        firmInfoTO.setLogoURL(firmInfoDTO.getLogoURL());
        firmInfoTO.setFullTimeEmployees(firmInfoDTO.getFullTimeEmployees());
        firmInfoTO.setUpdatedAt(firmInfoDTO.getUpdatedAt());


        return firmInfoTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
  public void initFirmEODInfoTOMapper() {

    Converter<FirmInfoTO, FirmInfoDTO> toUppercase = new AbstractConverter<FirmInfoTO, FirmInfoDTO>() {

      @Override
      protected FirmInfoDTO convert(FirmInfoTO firmEODInfoTO) {
        FirmInfoDTO firmInfoDTO = new FirmInfoDTO();
        firmInfoDTO.setCode(firmEODInfoTO.getCode());
        firmInfoDTO.setName(firmEODInfoTO.getName());
        firmInfoDTO.setType(firmEODInfoTO.getType());
        firmInfoDTO.setExchange(firmEODInfoTO.getExchange());
        firmInfoDTO.setCurrencyName(firmEODInfoTO.getCurrencyName());
        firmInfoDTO.setCurrencySymbol(firmEODInfoTO.getCurrencySymbol());
        firmInfoDTO.setCountryISO(firmEODInfoTO.getCountryISO());
        firmInfoDTO.setIsin(firmEODInfoTO.getIsin());
        firmInfoDTO.setcCusip(firmEODInfoTO.getCusip());
        firmInfoDTO.setcCik(firmEODInfoTO.getCik());
        firmInfoDTO.setEmployerIdNumber(firmEODInfoTO.getEmployerIdNumber());
        firmInfoDTO.setFiscalYearEnd(firmEODInfoTO.getFiscalYearEnd());
        firmInfoDTO.setiPODate(firmEODInfoTO.getiPODate());
        firmInfoDTO.setInternationalDomestic(firmEODInfoTO.getInternationalDomestic());
        firmInfoDTO.setSector(firmEODInfoTO.getSector());

        firmInfoDTO.setIndustry(firmEODInfoTO.getIndustry());
        firmInfoDTO.setGicSector(firmEODInfoTO.getGicSector());
        firmInfoDTO.setGicGroup(firmEODInfoTO.getGicGroup());
        firmInfoDTO.setGicIndustry(firmEODInfoTO.getGicIndustry());
        firmInfoDTO.setGicSubIndustry(firmEODInfoTO.getGicSubIndustry());
        firmInfoDTO.setDescription(firmEODInfoTO.getDescription());
        firmInfoDTO.setAddress(firmEODInfoTO.getAddress());

        firmInfoDTO.setPhone(firmEODInfoTO.getPhone());
        firmInfoDTO.setWebURL(firmEODInfoTO.getWebURL());
        firmInfoDTO.setLogoURL(firmEODInfoTO.getLogoURL());
        firmInfoDTO.setFullTimeEmployees(firmEODInfoTO.getFullTimeEmployees());
        firmInfoDTO.setUpdatedAt(firmEODInfoTO.getUpdatedAt());


        return firmInfoDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initFirmInfoMapper() {

    Converter<EODFirmInfosDTO, FirmInfoDTO> toUppercase = new AbstractConverter<EODFirmInfosDTO, FirmInfoDTO>() {

      @Override
      protected FirmInfoDTO convert(EODFirmInfosDTO eODFirmInfosDTO) {
        FirmInfoDTO firmInfoDTO = new FirmInfoDTO();
        firmInfoDTO.setCode(eODFirmInfosDTO.getCode());
        firmInfoDTO.setName(eODFirmInfosDTO.getName());
        firmInfoDTO.setType(eODFirmInfosDTO.getType());
        firmInfoDTO.setExchange(eODFirmInfosDTO.getExchange());
        firmInfoDTO.setCurrencyName(eODFirmInfosDTO.getCurrencyName());
        firmInfoDTO.setCurrencySymbol(eODFirmInfosDTO.getCurrencySymbol());
        firmInfoDTO.setCountryISO(eODFirmInfosDTO.getCountryISO());
        firmInfoDTO.setIsin(eODFirmInfosDTO.getISIN());
        firmInfoDTO.setcCusip(eODFirmInfosDTO.getCUSIP());
        firmInfoDTO.setcCik(eODFirmInfosDTO.getCIK());
        firmInfoDTO.setEmployerIdNumber(eODFirmInfosDTO.getEmployerIdNumber());
        firmInfoDTO.setFiscalYearEnd(eODFirmInfosDTO.getFiscalYearEnd());
        firmInfoDTO.setiPODate(eODFirmInfosDTO.getIPODate());
        firmInfoDTO.setInternationalDomestic(eODFirmInfosDTO.getInternationalDomestic());
        firmInfoDTO.setSector(eODFirmInfosDTO.getSector());

        firmInfoDTO.setIndustry(eODFirmInfosDTO.getIndustry());
        firmInfoDTO.setGicSector(eODFirmInfosDTO.getGicSector());
        firmInfoDTO.setGicGroup(eODFirmInfosDTO.getGicGroup());
        firmInfoDTO.setGicIndustry(eODFirmInfosDTO.getGicIndustry());
        firmInfoDTO.setGicSubIndustry(eODFirmInfosDTO.getGicSubIndustry());
        firmInfoDTO.setDescription(eODFirmInfosDTO.getDescription());
        firmInfoDTO.setAddress(eODFirmInfosDTO.getAddress());

        firmInfoDTO.setPhone(eODFirmInfosDTO.getPhone());
        firmInfoDTO.setWebURL(eODFirmInfosDTO.getWebURL());
        firmInfoDTO.setLogoURL(eODFirmInfosDTO.getLogoURL());
        firmInfoDTO.setFullTimeEmployees(eODFirmInfosDTO.getFullTimeEmployees());
        firmInfoDTO.setUpdatedAt(eODFirmInfosDTO.getUpdatedAt());


        return firmInfoDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
