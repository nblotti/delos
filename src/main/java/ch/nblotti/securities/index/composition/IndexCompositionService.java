package ch.nblotti.securities.index.composition;


import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


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


    List<IndexCompositionTO> loaded = indexCompositionDTOS.stream().map(x -> modelMapper.map(x, IndexCompositionTO.class)).collect(Collectors.toList());
    Iterable<IndexCompositionTO> saved = indexCompositionRepository.saveAll(loaded);

    return modelMapper.map(saved, new TypeToken<Iterable<IndexCompositionTO>>() {
    }.getType());

  }


  @PostConstruct
  public void initIndexCompositionTOMapper() {

    Converter<IndexCompositionTO, IndexCompositionDTO> toUppercase = new AbstractConverter<IndexCompositionTO, IndexCompositionDTO>() {

      @Override
      protected IndexCompositionDTO convert(IndexCompositionTO indexCompositionTO) {
        IndexCompositionDTO indexCompositionDTO = new IndexCompositionDTO();

        indexCompositionDTO.setId(indexCompositionTO.getId());

        indexCompositionDTO.setCode(indexCompositionTO.getCode());

        indexCompositionDTO.setActiveNow(indexCompositionTO.isActiveNow());

        indexCompositionDTO.setName(indexCompositionTO.getName());

        indexCompositionDTO.setDelisted(indexCompositionTO.isDelisted());

        indexCompositionDTO.setStartDate(indexCompositionTO.getStartDate());

        indexCompositionDTO.setEndDate(indexCompositionTO.getEndDate());

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

        indexCompositionTO.setCode(indexCompositionDTO.getCode());

        indexCompositionTO.setActiveNow(indexCompositionDTO.isActiveNow());

        indexCompositionTO.setName(indexCompositionDTO.getName());

        indexCompositionTO.setDelisted(indexCompositionDTO.isDelisted());

        indexCompositionTO.setStartDate(indexCompositionDTO.getStartDate());

        indexCompositionTO.setEndDate(indexCompositionDTO.getEndDate());


        return indexCompositionTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


}
