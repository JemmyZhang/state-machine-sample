package pers.jz.statemachine.listener;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.AbstractCompositeListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.event.CheckEvent;

import java.util.Iterator;

/**
 * @author jemmyzhang on 2018/3/29.
 */
public class CompositePersistStateChangeListener extends AbstractCompositeListener<PersistStateChangeListener> implements PersistStateChangeListener{

    @Override
    public void onPersist(State<CheckStatus, CheckEvent> state, Message<CheckEvent> message, Transition<CheckStatus, CheckEvent> transition, StateMachine<CheckStatus, CheckEvent> stateMachine) {
        Iterator<PersistStateChangeListener> iterator=getListeners().reverse();
        for (;iterator.hasNext();){
            PersistStateChangeListener listener = iterator.next();
            listener.onPersist(state,message,transition,stateMachine);
        }
    }
}