package sn.sdley.my_spring_boot_app2_security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sn.sdley.my_spring_boot_app2_security.model.User;
import sn.sdley.my_spring_boot_app2_security.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
