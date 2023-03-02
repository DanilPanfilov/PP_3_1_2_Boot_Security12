package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

@Service
public interface RoleService {
    List<Role> getRoles();

    void addRole(Role role);

    Role getRoleByName(String name);
     List<Role> getUniqAllRoles();


}
