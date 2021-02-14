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
  private IndexQuoteRepository indexQuoteRepository;

  @Autowired
  protected ModelMapper modelMapper;


  @Autowired
  protected DateTimeFormatter format1;



  public IndexQuoteDTO save(IndexQuoteDTO entity) {

    IndexQuoteTO indexQuoteTO = modelMapper.map(entity, IndexQuoteTO.class);

    IndexQuoteTO saved = indexQuoteRepository.save(indexQuoteTO);

    return modelMapper.map(saved, IndexQuoteDTO.class);

  }




}
