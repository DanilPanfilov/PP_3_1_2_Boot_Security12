package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/admin")
    public String admPage(){
        return "main";
    }

    @GetMapping("/user")
    public String userPage(){
        return "main";
    }
}
