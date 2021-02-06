package ch.nblotti.securities.index.composition;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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
  private EODIndexCompositionRepository EODIndexCompositionRepository;

  @Autowired
  private IndexCompositionRepository indexCompositionRepository;


  @Autowired
  protected ModelMapper modelMapper;


  @Autowired
  protected DateTimeFormatter format1;


  public List<IndexCompositionDTO> getIndexDataByDate(LocalDate runDate, String index) {

    List<IndexCompositionDTO> indexCompositionDTOs = new ArrayList<>();

    Collection<EODIndexCompositionDTO> EODIndexCompositionDTOS = EODIndexCompositionRepository.getIndexCompositionAtDate(runDate, index);

    for (EODIndexCompositionDTO current : EODIndexCompositionDTOS) {

      IndexCompositionDTO fHpost = modelMapper.map(current, IndexCompositionDTO.class);
      fHpost.setDate(runDate);
      indexCompositionDTOs.add(fHpost);

    }
    return indexCompositionDTOs;
  }

  public IndexCompositionDTO save(IndexCompositionDTO entity) {

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

  @PostConstruct
   void initEODIndexCompositionDTOMapper() {

    Converter<EODIndexCompositionDTO, IndexCompositionDTO> toUppercase = new AbstractConverter<EODIndexCompositionDTO, IndexCompositionDTO>() {

      @Override
      protected IndexCompositionDTO convert(EODIndexCompositionDTO eODIndexCompositionDTO) {

        IndexCompositionDTO indexCompositionDTO = new IndexCompositionDTO();

        indexCompositionDTO.setCode(eODIndexCompositionDTO.getCode());

        indexCompositionDTO.setExchange(eODIndexCompositionDTO.getExchange());

        indexCompositionDTO.setName(eODIndexCompositionDTO.getName());

        indexCompositionDTO.setSector(eODIndexCompositionDTO.getSector());

        indexCompositionDTO.setIndustry(eODIndexCompositionDTO.getIndustry());

        return indexCompositionDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
