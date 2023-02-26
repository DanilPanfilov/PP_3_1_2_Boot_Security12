package ru.kata.spring.boot_security.demo.utils;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Component
public class DbInit {
//    private final UserService userService;
//    private final RoleService roleService;
//
//    public DbInit(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @PostConstruct
//    private void init() {
//        try {
//            Role.getAllRoles().forEach(roleService::add);
//
//            HashSet<Role> roles = new HashSet<>();
//            roles.add(new Role(0, "ROLE_ADMIN"));
//            roles.add(new Role(1, "ROLE_USER"));
//            userService.add(
//                    new Person("Ад", "адм", 100,
//                            roles,
//                            "admin",
//                            "admin")
//            );
//        } catch (Exception exception) {/*ignore*/}
//    }
}
