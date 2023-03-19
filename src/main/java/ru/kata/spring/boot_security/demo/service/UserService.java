package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public interface UserService extends UserDetailsService{

    public List<User> getAllUsers();

    public User showUser(Long id);

    public void save(User user, String role);
    public void create(User user);

    public void update( User updateUser);

    public void delete(Long id);
    public User getUserByUsername(String username);
    public void setUserRoles(User user);
}
