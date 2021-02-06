package ch.nblotti.securities.index.quote;


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
public class IndexQuoteService {

  private static final Logger logger = Logger.getLogger("IndexQuoteService");

  public static final String INDEXES = "indexes";
  public static final String INDEX_MAP = "indexesMap";


  @Autowired
  private EODIndexQuoteRepository eodIndexQuoteRepository;


  @Autowired
  private IndexQuoteRepository indexQuoteRepository;

  @Autowired
  protected ModelMapper modelMapper;


  @Autowired
  protected DateTimeFormatter format1;


  public List<IndexQuoteDTO> getIndexDataByDate(LocalDate fromDate, LocalDate toDate, String index) {

    List<IndexQuoteDTO> indexQuoteDTOs = new ArrayList<>();

    Collection<EODIndexQuoteDTO> eODIndexQuoteDTOs = eodIndexQuoteRepository.getIndexDataByDate(fromDate, toDate, index);

    for (EODIndexQuoteDTO current : eODIndexQuoteDTOs) {

      IndexQuoteDTO fHpost = modelMapper.map(current, IndexQuoteDTO.class);
      indexQuoteDTOs.add(fHpost);

    }
    return indexQuoteDTOs;
  }

  public IndexQuoteDTO save(IndexQuoteDTO entity) {

    IndexQuoteTO indexQuoteTO = modelMapper.map(entity, IndexQuoteTO.class);

    IndexQuoteTO saved = indexQuoteRepository.save(indexQuoteTO);

    return modelMapper.map(saved, IndexQuoteDTO.class);

  }


  @PostConstruct
   void initIndexQuoteDTOMapper() {
    Converter<EODIndexQuoteDTO, IndexQuoteDTO> toUppercase = new AbstractConverter<EODIndexQuoteDTO, IndexQuoteDTO>() {

      @Override
      protected IndexQuoteDTO convert(EODIndexQuoteDTO eODIndexQuoteDTO) {
        IndexQuoteDTO indexQuoteDTO = new IndexQuoteDTO();

        indexQuoteDTO.setCode(eODIndexQuoteDTO.getCode());
        indexQuoteDTO.setDate(LocalDate.parse(eODIndexQuoteDTO.getDate(), format1));
        indexQuoteDTO.setOpen(eODIndexQuoteDTO.getOpen());
        indexQuoteDTO.setHigh(eODIndexQuoteDTO.getHigh());
        indexQuoteDTO.setLow(eODIndexQuoteDTO.getLow());
        indexQuoteDTO.setClose(eODIndexQuoteDTO.getClose());
        indexQuoteDTO.setAdjustedClose(eODIndexQuoteDTO.getAdjusted_close());
        indexQuoteDTO.setVolume(eODIndexQuoteDTO.getVolume());
        return indexQuoteDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }

  @PostConstruct
   void initIndexQuoteTOMapper() {
    Converter<EODIndexQuoteDTO, IndexQuoteTO> toUppercase = new AbstractConverter<EODIndexQuoteDTO, IndexQuoteTO>() {

      @Override
      protected IndexQuoteTO convert(EODIndexQuoteDTO eODIndexQuoteDTO) {
        IndexQuoteTO indexQuoteDTO = new IndexQuoteTO();

        indexQuoteDTO.setCode(eODIndexQuoteDTO.getCode());
        indexQuoteDTO.setDate(LocalDate.parse(eODIndexQuoteDTO.getDate(), format1));
        indexQuoteDTO.setOpen(eODIndexQuoteDTO.getOpen());
        indexQuoteDTO.setHigh(eODIndexQuoteDTO.getHigh());
        indexQuoteDTO.setLow(eODIndexQuoteDTO.getLow());
        indexQuoteDTO.setClose(eODIndexQuoteDTO.getClose());
        indexQuoteDTO.setAdjustedClose(eODIndexQuoteDTO.getAdjusted_close());
        indexQuoteDTO.setVolume(eODIndexQuoteDTO.getVolume());
        return indexQuoteDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }


  @PostConstruct
   void initEODIndexQuoteDTOMapper() {
    Converter<IndexQuoteTO, EODIndexQuoteDTO> toUppercase = new AbstractConverter< IndexQuoteTO,EODIndexQuoteDTO>() {

      @Override
      protected EODIndexQuoteDTO convert(IndexQuoteTO indexQuoteTO) {
        EODIndexQuoteDTO eODIndexQuoteDTO = new EODIndexQuoteDTO();

        eODIndexQuoteDTO.setCode(indexQuoteTO.getCode());
        eODIndexQuoteDTO.setDate(format1.format(indexQuoteTO.getDate()));
        eODIndexQuoteDTO.setOpen(indexQuoteTO.getOpen());
        eODIndexQuoteDTO.setHigh(indexQuoteTO.getHigh());
        eODIndexQuoteDTO.setLow(indexQuoteTO.getLow());
        eODIndexQuoteDTO.setClose(indexQuoteTO.getClose());
        eODIndexQuoteDTO.setAdjusted_close(indexQuoteTO.getAdjustedClose());
        eODIndexQuoteDTO.setVolume(indexQuoteTO.getVolume());
        return eODIndexQuoteDTO;
      }
    };

    modelMapper.addConverter(toUppercase);

  }



}
