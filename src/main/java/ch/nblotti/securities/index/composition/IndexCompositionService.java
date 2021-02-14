package ch.nblotti.securities.index.composition;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;


@Service
public class IndexCompositionService {

  private static final Logger logger = Logger.getLogger("IndexCompositionService");

  public static final String INDEXES = "indexes";
  public static final String INDEX_MAP = "indexesMap";


  @Autowired
  private IndexCompositionRepository indexCompositionRepository;


  @Autowired
  protected ModelMapper modelMapper;


  @Autowired
  protected DateTimeFormatter format1;



  public Iterable<IndexCompositionDTO> saveIndexComposition(Collection<IndexCompositionDTO> indexCompositionDTOS) {

    Iterable<IndexCompositionTO> loaded = modelMapper.map(indexCompositionDTOS, new TypeToken<Iterable<IndexCompositionTO>>() {
    }.getType());

    Iterable<IndexCompositionTO> saved = indexCompositionRepository.saveAll(loaded);

    return modelMapper.map(saved, new TypeToken<Iterable<IndexCompositionTO>>() {
    }.getType());

  }


  IndexCompositionDTO save(IndexCompositionDTO entity) {

    IndexCompositionTO indexCompositionTO = modelMapper.map(entity, IndexCompositionTO.class);

    IndexCompositionTO saved = indexCompositionRepository.save(indexCompositionTO);

    return modelMapper.map(saved, IndexCompositionDTO.class);

  }


  @PostConstruct
  public void initIndexCompositionTOMapper() {

    Converter<IndexCompositionTO, IndexCompositionDTO> toUppercase = new AbstractConverter<IndexCompositionTO, IndexCompositionDTO>() {

      @Override
      protected IndexCompositionDTO convert(IndexCompositionTO indexCompositionTO) {
        IndexCompositionDTO indexCompositionDTO = new IndexCompositionDTO();

        indexCompositionDTO.setId(indexCompositionTO.getId());

        indexCompositionDTO.setDate(indexCompositionTO.getDate());

        indexCompositionDTO.setCode(indexCompositionTO.getCode());

        indexCompositionDTO.setExchange(indexCompositionTO.getExchange());

        indexCompositionDTO.setName(indexCompositionTO.getName());

        indexCompositionDTO.setSector(indexCompositionTO.getSector());

        indexCompositionDTO.setIndustry(indexCompositionTO.getIndustry());

        return indexCompositionDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
  void initIndexCompositionDTOMapper() {

    Converter<IndexCompositionDTO, IndexCompositionTO> toUppercase = new AbstractConverter<IndexCompositionDTO, IndexCompositionTO>() {

      @Override
      protected IndexCompositionTO convert(IndexCompositionDTO indexCompositionDTO) {
        IndexCompositionTO indexCompositionTO = new IndexCompositionTO();

        indexCompositionTO.setId(indexCompositionDTO.getId());

        indexCompositionTO.setDate(indexCompositionDTO.getDate());

        indexCompositionTO.setCode(indexCompositionDTO.getCode());

        indexCompositionTO.setExchange(indexCompositionDTO.getExchange());

        indexCompositionTO.setName(indexCompositionDTO.getName());

        indexCompositionTO.setSector(indexCompositionDTO.getSector());

        indexCompositionTO.setIndustry(indexCompositionDTO.getIndustry());

        return indexCompositionTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
