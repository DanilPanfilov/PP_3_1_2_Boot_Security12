package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
//@RequestMapping("/admin")
public class AdminController {
    // в КЛАССЕ может быть косяк из-за Validated он должен быть Valid
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String indexPage() {
        return "/index";
    }

    @GetMapping("/admin")// то что указывается в браузере
    public String getAdminPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/ADMIN/admin";// на какую hml страницу смотрим
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
//        List<Role> roles = roleService.getRoles();
//        model.addAttribute("allRoles", roles);
        model.addAttribute("user", new User());
        return "/ADMIN/registration";
    }

    @PostMapping("/registration")
    public String useRegistration(@ModelAttribute("user") @Validated User user,
                                  @ModelAttribute("role") String role,
                                  Model model,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/ADMIN/registration";
        }
        try {
            userService.save(user, role);
        } catch (Exception e) {
            model.addAttribute("usernameError", "Пользователь стаким именем уже существует");
            return "/ADMIN/registration";
        }
        return "redirect:/";// Был /admin
    }

    @GetMapping("/admin/new")
    public String registrationPageByAdmin(Model model) {
        model.addAttribute("user", new User());
        return "/ADMIN/new";
    }

    @PostMapping("/admin/new")
    public String useRegistrationByAdmin(@ModelAttribute("user") @Validated User user,
                                         @ModelAttribute("role") String role,
                                         Model model) {
        try {
            userService.save(user, role);
        } catch (Exception e) {
            model.addAttribute("usernameError", "Пользователь стаким именем уже существует");
            return "/ADMIN/new";
        }
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        User user = userService.showUser(id);
//        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRoles().stream().anyMatch(el -> el.getName().equals("ROLE_ADMIN")));
        List<Role> roles = roleService.getUniqAllRoles();
        model.addAttribute("rolesAdd", roles);
//        model.addAttribute("isUser",user.getRoles().stream().anyMatch(el-> el.getName().equals("ROLE_USER")));
        return "/ADMIN/edit";
    }

    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") @Validated User user, @PathVariable("id") Long id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/ADMIN/edit";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}