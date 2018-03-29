package pers.jz.statemachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.event.CheckEvent;
import pers.jz.statemachine.handler.PersistStateMachineHandler;
import pers.jz.statemachine.listener.CheckPersistStateChangeListener;

import javax.annotation.Resource;
import java.util.EnumSet;

/**
 * @author jemmyzhang on 2018/3/29.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<CheckStatus,CheckEvent>{

    @Resource
    private StateMachine<CheckStatus,CheckEvent> stateMachine;

    @Override
    public void configure(StateMachineStateConfigurer<CheckStatus, CheckEvent> states) throws Exception {
        states.withStates()
                .initial(CheckStatus.UNCHECKED)
                .states(EnumSet.allOf(CheckStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<CheckStatus, CheckEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(CheckStatus.UNCHECKED).target(CheckStatus.PASSED)
                .event(CheckEvent.PASS)
                .and()
                .withExternal()
                .source(CheckStatus.UNCHECKED).target(CheckStatus.UNPASSED)
                .event(CheckEvent.UNPASS);
    }

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        PersistStateMachineHandler persistStateMachineHandler = new PersistStateMachineHandler(stateMachine);
        persistStateMachineHandler.addPersistStateChangeListener(checkPersistStateChangeListener());
        return persistStateMachineHandler;
    }

    @Bean
    public CheckPersistStateChangeListener checkPersistStateChangeListener(){
        return new CheckPersistStateChangeListener();
    }
}
