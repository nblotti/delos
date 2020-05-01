package ch.nblotti.securities.firm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface MarketCapRepository extends CrudRepository<MarketCapDto, Long> {


}
