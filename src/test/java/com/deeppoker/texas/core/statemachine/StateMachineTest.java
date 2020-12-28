package com.deeppoker.texas.core.statemachine;

import com.google.common.collect.Maps;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;
import java.util.Map;

public class StateMachineTest {
    public static void main(String[] args) throws Exception {
        StateMachineTest test = new StateMachineTest();
        StateMachine<State, Event> stateMachine = test.buildMachine();
        stateMachine.start();


        boolean result = stateMachine.sendEvent(new Message<Event>() {
            @Override
            public Event getPayload() {
                return Event.E1;
            }

            @Override
            public MessageHeaders getHeaders() {
                Map<String, Object> map = Maps.newHashMap();
                map.put("test", "hello1");
                return new MessageHeaders(map);
            }
        });

        System.out.println(result);
        System.out.println(stateMachine.getState().getId());
        result = stateMachine.sendEvent(new Message<Event>() {
            @Override
            public Event getPayload() {
                return Event.E1;
            }

            @Override
            public MessageHeaders getHeaders() {
                Map<String, Object> map = Maps.newHashMap();
                map.put("test", "hello");
                return new MessageHeaders(map);
            }
        });
        System.out.println(result);
        System.out.println(stateMachine.getState().getId());
    }


    public StateMachine<State, Event> buildMachine() throws Exception {
        StateMachineBuilder.Builder<State, Event> builder = StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(State.S1)
                .states(EnumSet.allOf(State.class));

        builder.configureTransitions()
                .withExternal()
                .source(State.S1)
                .target(State.S2)
                .action(action())
                .guard(new Guard<State, Event>() {
                    @Override
                    public boolean evaluate(StateContext<State, Event> context) {
                        String object = context.getMessageHeaders().get("test", String.class);
                        if ("hello".equals(object)) {
                            return true;
                        }
                        return false;
                    }
                })
                .event(Event.E1)
                .and()
                .withExternal()
                .source(State.S2).target(State.S1)
                .action(action())
                .event(Event.E2);

        return builder.build();
    }


    public Action<State, Event> action() {
        return System.out::println;
    }
}
