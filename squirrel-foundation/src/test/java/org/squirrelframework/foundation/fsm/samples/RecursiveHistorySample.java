package org.squirrelframework.foundation.fsm.samples;

import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.HistoryType;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;

enum SampleStates {
    L1_A, L1_B, L1_C, L2_A, L2_B, L2_C, L31_A, L31_B, L32_A, L32_B
}

enum SampleEvents {
    E1, E2, E3, E4, E5, E6, E7, E8
}

class SampleContext {
}

@States({
        @State(name = "L1_A", initialState = true),
        @State(name = "L1_B", historyType = HistoryType.RECURSIVE),
        @State(name = "L1_C"),
        @State(name = "L2_A", parent = "L1_B", initialState = true),
        @State(name = "L2_B", parent = "L1_B", historyType = HistoryType.RECURSIVE),
        @State(name = "L2_C", parent = "L1_B"),
        @State(name = "L31_A", parent = "L2_B", initialState = true),
        @State(name = "L31_B", parent = "L2_B"),
        @State(name = "L32_A", parent = "L2_C", initialState = true),
        @State(name = "L32_B", parent = "L2_C"),
})
@Transitions({
        @Transit(from = "L1_A", to = "L1_B", on = "E1"),
        @Transit(from = "L2_A", to = "L2_B", on = "E2"),
        @Transit(from = "L2_A", to = "L2_C", on = "E3"),
        @Transit(from = "L31_A", to = "L31_B", on = "E4"),
        @Transit(from = "L32_A", to = "L32_B", on = "E5"),
        @Transit(from = "L1_B", to = "L1_C", on = "E6"),
        @Transit(from = "L1_C", to = "L1_B", on = "E7"),
        @Transit(from = "L1_C", to = "L2_A", on = "E8"),
})
public class RecursiveHistorySample extends
        AbstractStateMachine<RecursiveHistorySample, SampleStates, SampleEvents, SampleContext> {

    public static RecursiveHistorySample create() {
        StateMachineBuilder<RecursiveHistorySample, SampleStates, SampleEvents, SampleContext> builder = StateMachineBuilderFactory
                .create(RecursiveHistorySample.class, SampleStates.class, SampleEvents.class, SampleContext.class);

        return builder.newStateMachine(SampleStates.L1_A);
    }

    public static void main(String[] args) {
        final SampleContext sampleContext = new SampleContext();

        RecursiveHistorySample sampleController = RecursiveHistorySample.create();

        // Demonstrate recursive history through two levels
        sampleController.fire(SampleEvents.E1);
        sampleController.fire(SampleEvents.E2);
        sampleController.fire(SampleEvents.E4);
        System.out.println("Current state should be L31_B: " + sampleController.getCurrentState());
        sampleController.fire(SampleEvents.E6);
        System.out.println("Current state should be L1_C : " + sampleController.getCurrentState());
        sampleController.fire(SampleEvents.E7);
        System.out.println("Current state should be L31_B: " + sampleController.getCurrentState());

        // Start back, do not use history
        sampleController.fire(SampleEvents.E6);
        sampleController.fire(SampleEvents.E8);

        // Demonstrate recursive history through one level only, using initial state on second level
        sampleController.fire(SampleEvents.E3);
        sampleController.fire(SampleEvents.E5);
        System.out.println("Current state should be L32_B: " + sampleController.getCurrentState());
        sampleController.fire(SampleEvents.E6);
        System.out.println("Current state should be L1_C : " + sampleController.getCurrentState());
        sampleController.fire(SampleEvents.E7);
        System.out.println("Current state should be L32_A: " + sampleController.getCurrentState());
    }
}