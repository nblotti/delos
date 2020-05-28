package ch.nblotti.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
@EnableScheduling
public class JpaDao {


  @Autowired
  private DataSource dataSource;

  private boolean refreshRequested = false;


  @PostConstruct
  public void init() {

  }

  @Scheduled(fixedDelay = 5000)
  protected void refrehMaterializedViews() {
    if (refreshRequested) {
      refreshRequested = false;
      JdbcTemplate template = new JdbcTemplate(dataSource);
      template.execute("select refresh_fn ()");

    }
  }

  public void requireRefresh() {
    this.refreshRequested = true;
  }

}
