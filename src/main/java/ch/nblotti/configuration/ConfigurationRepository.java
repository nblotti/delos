package ch.nblotti.configuration;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface ConfigurationRepository extends PagingAndSortingRepository<ConfigTO, Long> {

  public List<ConfigTO> findAllByCodeAndTypeOrderByIdDesc(String code, String type);
}
