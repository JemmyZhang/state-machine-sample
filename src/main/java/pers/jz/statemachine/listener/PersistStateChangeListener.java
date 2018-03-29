package pers.jz.statemachine.listener;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.event.CheckEvent;


/**
 * @author jemmyzhang on 2018/3/29.
 */
public interface PersistStateChangeListener {

    void onPersist(State<CheckStatus,CheckEvent> state, Message<CheckEvent> message,
                   Transition<CheckStatus,CheckEvent> transition, StateMachine<CheckStatus,CheckEvent> stateMachine);
}
