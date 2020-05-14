package ch.nblotti.securities.index.sp500.respository;

import ch.nblotti.securities.firm.to.ConfigTO;
import ch.nblotti.securities.index.sp500.to.Sp500IndexSectorIndustryTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional()
public interface ConfigRepository extends CrudRepository<ConfigTO, Integer> {

  public Optional<ConfigTO> findByCodeAndType(String type, String key);

  ConfigTO saveAndFlush(ConfigTO configTO);
}
