package ch.nblotti.securities.index.sp500;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Sp500LoaderStateMachine.class})
public class Sp500LoaderStateMachineTest {


  @Autowired
  private StateMachine<LOADER_STATES, LOADER_EVENTS> sp500LoaderStateMachine;

  @Test
  public void testEndOfMonth() throws Exception {

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
        .expectStates(LOADER_STATES.DETERMINING_DATE)
        .and()
        .build();
    plan.test();


  }


}
