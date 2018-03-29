package pers.jz.statemachine.interceptor;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.event.CheckEvent;
import pers.jz.statemachine.listener.PersistStateChangeListener;

/**
 * @author jemmyzhang on 2018/3/29.
 */
public class PersistingStateChangeInterceptor extends StateMachineInterceptorAdapter<CheckStatus, CheckEvent> {


    PersistStateChangeListener listener;

    public PersistingStateChangeInterceptor(PersistStateChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void preStateChange(State<CheckStatus, CheckEvent> state, Message<CheckEvent> message, Transition<CheckStatus, CheckEvent> transition, StateMachine<CheckStatus, CheckEvent> stateMachine) {
        listener.onPersist(state, message, transition, stateMachine);
    }
}