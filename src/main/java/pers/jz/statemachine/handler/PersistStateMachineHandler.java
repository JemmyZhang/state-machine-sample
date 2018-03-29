package pers.jz.statemachine.handler;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.event.CheckEvent;
import pers.jz.statemachine.interceptor.PersistingStateChangeInterceptor;
import pers.jz.statemachine.listener.CompositePersistStateChangeListener;
import pers.jz.statemachine.listener.PersistStateChangeListener;

import java.util.List;

/**
 * @author jemmyzhang on 2018/3/29.
 */
public class PersistStateMachineHandler extends LifecycleObjectSupport{

    private StateMachine<CheckStatus,CheckEvent> stateMachine;

    private final CompositePersistStateChangeListener listeners = new CompositePersistStateChangeListener();
    private final PersistingStateChangeInterceptor interceptor = new PersistingStateChangeInterceptor(listeners);

    public PersistStateMachineHandler(StateMachine<CheckStatus,CheckEvent> stateMachine) {
        this.stateMachine=stateMachine;
    }

    public void addPersistStateChangeListener(PersistStateChangeListener listener) {
        listeners.register(listener);
    }

    public boolean handleEventWithState(Message<CheckEvent> event,CheckStatus status){
        stateMachine.stop();
        List<StateMachineAccess<CheckStatus, CheckEvent>> stateMachineAccesses =    stateMachine.getStateMachineAccessor().withAllRegions();
        for(StateMachineAccess<CheckStatus,CheckEvent> item: stateMachineAccesses){
            item.resetStateMachine(new DefaultStateMachineContext<>(status,null,null,null));
        }
        stateMachine.start();
        return stateMachine.sendEvent(event);
    }

    @Override
    protected void onInit() throws Exception {
        stateMachine.getStateMachineAccessor().doWithAllRegions(var -> var.addStateMachineInterceptor(interceptor));
    }
}
