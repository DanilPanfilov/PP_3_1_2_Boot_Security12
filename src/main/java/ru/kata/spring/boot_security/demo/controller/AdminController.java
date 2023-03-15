//package ru.kata.spring.boot_security.demo.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.security.Principal;
//
//
//@Controller
//public class AdminController {
//
//    private final UserService userService;
//
//    private final RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping("/")
//    public String indexPage() {
//        return "/index";
//    }
//
//    @GetMapping("/admin")// то что указывается в браузере
//    public String getAdminPage(Principal principal, Model model) {
//        User user = userService.getUserByUsername(principal.getName());
//        model.addAttribute("user", user);
//        model.addAttribute("users", userService.getAllUsers());
//        model.addAttribute("allRoles", roleService.getUniqAllRoles());
//        return "/ADMIN/admin";// на какую hml страницу смотрим
//    }
//
//    @GetMapping("/registration")
//    public String registrationPage(Model model) {
//
//        model.addAttribute("user", new User());
//        model.addAttribute("rolesList", roleService.getRoles());
//        return "/ADMIN/registration";
//    }
//
//    @PostMapping("/registration")
//    public String useRegistration(@ModelAttribute("user") @Validated User user,
//                                  Model model,
//                                  BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/ADMIN/registration";
//        }
//        try {
//            userService.create(user);
//        } catch (Exception e) {
//            model.addAttribute("usernameError", "Пользователь стаким именем уже существует");
//            return "/ADMIN/registration";
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping("/admin/new")
//    public String registrationPageByAdmin(Model model, Principal principal) {
//        User user = userService.getUserByUsername(principal.getName());
//        model.addAttribute("user", new User());
//        model.addAttribute("user1",user);
//        model.addAttribute("rolesList", roleService.getRoles());
//        return "/ADMIN/new";
//    }
//
//    @PostMapping("/admin/new")
//    public String useRegistrationByAdmin(@ModelAttribute("user") @Validated User user,
//                                         @ModelAttribute("role") String role,
//                                         Model model) {
//        try {
//            userService.create(user);
//        } catch (Exception e) {
//            model.addAttribute("usernameError", "Пользователь стаким именем уже существует");
//            return "/ADMIN/new";
//        }
//        return "redirect:/admin";
//    }
//
//    @PatchMapping("/{id}/edit")
//    public String update(@ModelAttribute("user") @Validated User user,
//                         @ModelAttribute("role") String role,
//                         @PathVariable("id") Long id,
//                         BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "/ADMIN/admin";
//        }
////        User user = userService.getUserByUsername(principal.getName());
//        userService.update(id, user, role);
//        return "redirect:/admin";
//    }
//
//    @DeleteMapping("/{id}/delete")
//    public String delete(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return "redirect:/admin";
//    }
//}