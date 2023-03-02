package ru.kata.spring.boot_security.demo.utils;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.annotation.PostConstruct;

@Component
public class DbInit {
    private final UserService userService;
    private final RoleService roleService;

    public DbInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void init() {
//        try {
//            Role admin = roleService.getRoleByName("ADMIN");
//            if (admin == null) {
//                Role adminRole = new Role("ADMIN");
//                roleService.addRole(adminRole);
//            }
//            Role user = roleService.getRoleByName("USER");
//            if (user == null) {
//                Role userRole = new Role("USER");
//                roleService.addRole(userRole);
//            }
//        } catch (Exception e) {
//
//        }

        try {
            User tryUserAdmin = userService.getUserByUsername("ADMIN");
            if (tryUserAdmin == null) {
                User userAdmin = new User();
                Role admin1 = roleService.getRoleByName("ROLE_ADMIN");
                userAdmin.setUsername("ADMIN");
                userAdmin.setPassword("admin");
                userAdmin.setName("юзер");
                userAdmin.setLastName("юзеров");
                userAdmin.addRole(admin1);
                userService.save(userAdmin, "ROLE_ADMIN");
            }
            User tryUserUser = userService.getUserByUsername("USER");
            if (tryUserUser == null) {
                User userUser = new User();
                Role user1 = roleService.getRoleByName("ROLE_USER");
                userUser.addRole(user1);
                userUser.setUsername("USER");
                userUser.setPassword("user");
                userUser.setName("админ");
                userUser.setLastName("админов");
                userService.save(userUser, "ROLE_USER");
            }
        } catch (Exception exception) {

        }
    }
}

