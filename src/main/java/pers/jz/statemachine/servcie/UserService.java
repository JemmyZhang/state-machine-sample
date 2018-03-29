package pers.jz.statemachine.servcie;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pers.jz.statemachine.dao.UserRepository;
import pers.jz.statemachine.entity.User;
import pers.jz.statemachine.enums.CheckStatus;
import pers.jz.statemachine.enums.MasterStatus;
import pers.jz.statemachine.event.CheckEvent;
import pers.jz.statemachine.handler.PersistStateMachineHandler;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author jemmyzhang on 2018/3/29.
 */

@Service
public class UserService {

    @Resource
    UserRepository userRepository;

    @Resource
    PersistStateMachineHandler persistStateMachineHandler;

    public void add(String name) {
        User user = new User();
        user.setName(name);
        List<User> byMaster = userRepository.findByMaster(MasterStatus.MASTER);
        if (CollectionUtils.isEmpty(byMaster)) {
            user.setMaster(MasterStatus.MASTER);
            user.setPassed(CheckStatus.PASSED);
        } else {
            user.setMaster(MasterStatus.STUDENT);
            user.setPassed(CheckStatus.UNCHECKED);
        }
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void clear() {
        userRepository.deleteAll();
    }

    public boolean check(Long masterId, Long userId, CheckEvent event) {
        User master = userRepository.findOne(masterId);
        User user = userRepository.findOne(userId);
        if (Objects.isNull(master) || Objects.isNull(user) || !Objects.equals(master.getMaster(), MasterStatus.MASTER)) {
            return false;
        }
        return persistStateMachineHandler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("userId", userId).build(), user.getPassed());
    }
}
