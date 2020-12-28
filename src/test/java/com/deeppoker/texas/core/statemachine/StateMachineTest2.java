package com.deeppoker.texas.core.statemachine;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

public class StateMachineTest2 {

    public StateMachine<State, Event> buildMachine() throws Exception {
        StateMachineBuilder.Builder<State, Event> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(State.S1)
                .states(EnumSet.allOf(State.class));


        //builder.configureTransitions().withChoice()

        //.first()
        return builder.build();
    }

}
