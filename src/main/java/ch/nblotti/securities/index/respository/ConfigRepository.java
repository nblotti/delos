package ch.nblotti.securities.index.respository;

import ch.nblotti.securities.firm.to.ConfigTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
@Transactional()
public interface ConfigRepository extends CrudRepository<ConfigTO, Integer> {

  public Optional<ConfigTO> findByCodeAndType(String type, String key);

  ConfigTO saveAndFlush(ConfigTO configTO);
}
