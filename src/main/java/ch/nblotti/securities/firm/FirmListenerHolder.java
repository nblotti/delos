package ch.nblotti.securities.firm;

import ch.nblotti.securities.JpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirmListenerHolder {

  public static JpaDao jpaDao;

  @Autowired
  public void setAuditService(JpaDao jpaDao) {
    FirmListenerHolder.jpaDao = jpaDao;
  }


}
