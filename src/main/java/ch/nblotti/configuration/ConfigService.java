package ch.nblotti.configuration;


import ch.nblotti.securities.firm.infos.FirmInfoDTO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ConfigService {


  @Autowired
  ConfigurationRepository configurationRepository;

  @Autowired
  protected ModelMapper modelMapper;

  @Transactional
  public List<ConfigDTO> findAllByCodeAndType(String code, String type) {

    List<ConfigTO> configDTOS = configurationRepository.findAllByCodeAndTypeOOrderByIdDesc(code, type);


    return StreamSupport.stream(configDTOS.spliterator(), false)
      .map(n -> modelMapper.map(n, ConfigDTO.class))
      .collect(Collectors.toList());

  }


  @PostConstruct
  public void initFirmInfoDTOMapper() {

    Converter<ConfigDTO, ConfigTO> toUppercase = new AbstractConverter<ConfigDTO, ConfigTO>() {

      @Override
      protected ConfigTO convert(ConfigDTO configDTO) {
        ConfigTO configTO = new ConfigTO();
        configTO.setId(configDTO.getId());
        configTO.setCode(configDTO.getCode());
        configTO.setType(configDTO.getType());
        configTO.setValue(configDTO.getValue());
        return configTO;
      }
    };
    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  public void initFirmInfoTOMapper() {

    Converter<ConfigTO, ConfigDTO> toUppercase = new AbstractConverter<ConfigTO, ConfigDTO>() {

      @Override
      protected ConfigDTO convert(ConfigTO configTO) {
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setId(configTO.getId());
        configDTO.setCode(configTO.getCode());
        configDTO.setType(configTO.getType());
        configDTO.setValue(configTO.getValue());

        return configDTO;
      }
    };
    modelMapper.addConverter(toUppercase);

  }


  public ConfigDTO save(ConfigDTO configDTO) {

    ConfigTO configTO = modelMapper.map(configDTO, ConfigTO.class);

    ConfigTO saved = this.configurationRepository.save(configTO);

    return modelMapper.map(configTO, ConfigDTO.class);

  }

  public void deleteById(Long id) {
    this.configurationRepository.deleteById(id);
  }


    public Optional<ConfigDTO> findById(Long id) {

    Optional<ConfigTO> returned = this.configurationRepository.findById(id);

    if (returned.isPresent())
      return Optional.of(modelMapper.map(returned.get(), ConfigDTO.class));
    else
      return Optional.empty();
  }
}



