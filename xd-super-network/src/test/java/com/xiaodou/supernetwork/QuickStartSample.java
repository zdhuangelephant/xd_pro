package com.xiaodou.supernetwork;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.TransitionType;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

public class QuickStartSample {

  // 1. Define State Machine Event
  enum FSMEvent {
    ToA, ToB, ToC, ToD, reB
  }

  enum FSMState {
    A, B
  }

  // 2. Define State Machine Class
  @States({@State(name = "A"), @State(name = "B", entryCallMethod = "ontoB")})
  @Transitions({
      @Transit(from = "A", to = "B", on = "ToB", callMethod = "fromAToB"),
      @Transit(from = "B", to = "B", on = "reB", callMethod = "fromBToB", type = TransitionType.INTERNAL)})
  @StateMachineParameters(stateType = FSMState.class, eventType = FSMEvent.class, contextType = Integer.class)
  public interface IStateMachine extends UntypedStateMachine {

  }

  static class StateMachineSample extends AbstractUntypedStateMachine implements IStateMachine {
    protected void fromAToB(FSMState from, FSMState to, FSMEvent event, Integer context) {
      System.out.println("Transition from '" + from + "' to '" + to + "' on event '" + event
          + "' with context '" + context + "'.");
    }

    protected void fromBToB(FSMState from, FSMState to, FSMEvent event, Integer context) {
      System.out.println("Transition from '" + from + "' to '" + to + "' on event '" + event
          + "' with context '" + context + "'.");
    }

    protected void ontoB(FSMState from, FSMState to, FSMEvent event, Integer context) {
      System.out.println("Entry State \'" + to + "\'.");
    }
  }

  static class FinalMachine extends StateMachineSample {

  }

  public static void main(String[] args) {
    // 3. Build State Transitions
    UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(FinalMachine.class);

    // 4. Use State Machine
    UntypedStateMachine fsm = builder.newStateMachine(FSMState.A);
    fsm.fire(FSMEvent.ToB, 10);
    fsm.fire(FSMEvent.reB);
    fsm.fire(FSMEvent.ToC);

    System.out.println("Current state is " + fsm.getCurrentState());
  }
}
