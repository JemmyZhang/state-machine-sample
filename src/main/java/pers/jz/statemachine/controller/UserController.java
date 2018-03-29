package pers.jz.statemachine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.jz.statemachine.entity.User;
import pers.jz.statemachine.event.CheckEvent;
import pers.jz.statemachine.servcie.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jemmyzhang on 2018/3/29.
 */
@RestController
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/")
    public String root() {
        return "root";
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/users/add")
    public void add(@RequestParam String name) {
        userService.add(name);
    }

    @GetMapping("/users/clear")
    public void clear() {
        userService.clear();
    }

    @GetMapping("/users/check")
    public boolean check(@RequestParam Long master, @RequestParam Long user, @RequestParam CheckEvent event) {
        return userService.check(master, user, event);
    }
}
