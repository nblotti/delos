package ch.nblotti.securities.firm;

import ch.nblotti.securities.JpaDao;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Iterator;

public class FirmListener extends EmptyInterceptor {

  public JpaDao jpaDao;

  public FirmListener() {

  }

  public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    FirmListenerHolder.jpaDao.requireRefresh();
  }

  public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
    FirmListenerHolder.jpaDao.requireRefresh();
    return false;
  }

  public void afterTransactionBegin(Transaction tx) {
    FirmListenerHolder.jpaDao.requireRefresh();
  }

  public void afterTransactionCompletion(Transaction tx) {
    FirmListenerHolder.jpaDao.requireRefresh();
  }


}
