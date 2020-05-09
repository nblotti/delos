package ch.nblotti.securities.index.sp500;

import ch.nblotti.securities.loader.DailyLoaderStateMachine;
import ch.nblotti.securities.loader.LOADER_EVENTS;
import ch.nblotti.securities.loader.LOADER_STATES;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DailyLoaderStateMachine.class})
public class DailyLoaderStateMachineTest {


  @Autowired
  private StateMachine<LOADER_STATES, LOADER_EVENTS> sp500LoaderStateMachine;

  @Test
  public void testEndOfMonth() throws Exception {
/*
    StateMachineTestPlan<LOADER_STATES, LOADER_EVENTS> plan =
      StateMachineTestPlanBuilder.<LOADER_STATES, LOADER_EVENTS>builder()
        .defaultAwaitTime(2)
        .stateMachine(sp500LoaderStateMachine)
        .step()
        .expectStates(LOADER_STATES.READY)
        .and()
        .step()
        .sendEvent(LOADER_EVENTS.EVENT_RECEIVED)
        .expectStateChanged(1)
        .expectStates(LOADER_STATES.END_OF_MONTH)
        .and()
        .build();
    plan.test();
*/

  }


}
