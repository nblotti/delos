package ch.nblotti.securities.firm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface FirmRepository extends CrudRepository<FirmPO, Long> {

  Optional<FirmPO> findByExchangeAndCode(String exchange, String code);


}
