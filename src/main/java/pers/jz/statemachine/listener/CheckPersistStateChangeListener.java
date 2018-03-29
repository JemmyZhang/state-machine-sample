package pers.jz.statemachine.listener;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import pers.jz.statemachine.dao.UserRepository;
import pers.jz.statemachine.entity.User;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.event.CheckEvent;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author jemmyzhang on 2018/3/29.
 */

public class CheckPersistStateChangeListener implements PersistStateChangeListener {

    @Resource
    private UserRepository userRepository;


    @Override
    public void onPersist(State<CheckStatus, CheckEvent> state, Message<CheckEvent> message, Transition<CheckStatus, CheckEvent> transition, StateMachine<CheckStatus, CheckEvent> stateMachine) {
        if (Objects.nonNull(message) && message.getHeaders().containsKey("userId")) {
            Long userId = message.getHeaders().get("userId", Long.class);
            User user = userRepository.findOne(userId);
            CheckStatus checkStatus = state.getId();
            user.setPassed(checkStatus);
            userRepository.save(user);
        }
    }
}
