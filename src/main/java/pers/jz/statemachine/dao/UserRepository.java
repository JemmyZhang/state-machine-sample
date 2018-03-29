package pers.jz.statemachine.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.jz.statemachine.entity.User;
import pers.jz.statemachine.enums.MasterStatus;

import java.util.List;

/**
 * @author jemmyzhang on 2018/3/29.
 */
public interface UserRepository extends JpaRepository<User,Long>{

    List<User> findByMaster(MasterStatus master);
}
