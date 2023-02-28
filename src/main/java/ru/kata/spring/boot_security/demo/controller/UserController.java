package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show")
    public String user(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        return "/USER/show";
    }

//    private UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("user") User user) {
//        return "registration";
//    }
    //до сюда


}
