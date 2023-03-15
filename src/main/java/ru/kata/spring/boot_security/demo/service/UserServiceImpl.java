package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User getUserByUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        return byUsername.orElse(null);
    }

    @Override
    @Transactional
    public User showUser(Long id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("This ID not found");
        } else {
            return user.get();
        }
    }

    @Override
    @Transactional
    public void save(User user, String role) {
        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        if (user1.isPresent()) {
            throw new UsernameNotFoundException("Пользователь с таким именем уже существует");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role roleByName = roleService.getRoleByName(role);
        user.setRoles(Collections.singleton(roleByName));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void create(User user) {
//        user.setRoles(new HashSet<>(roleRepository.saveAll(user.getRoles())));
        setUserRoles(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    //    @Override
//    @Transactional
//    public void update(User updateUser) {
//        Optional<User> byId = userRepository.findById(updateUser.getId());
//        updateUser.setPassword(byId.get().getPassword());
//        updateUser.getRoles().stream().forEach(s -> {
//            Role roleByName = roleService.getRoleByName(s.getName());
//            if (roleByName != null) {
//                byId.get().addRole(roleByName);
//            }
//        });
////        if (byId.isPresent()) {
////            User user = byId.get();
////            user.setName(updateUser.getName());
////            user.setLastName(updateUser.getLastName());
////            user.setUsername(updateUser.getUsername());
//        userRepository.save(updateUser);
//
//    }
    @Transactional
    @Override
    public void update(User user) {
        Optional<User> byId = userRepository.findById(user.getId());
        user.setPassword(byId.get().getPassword());
        setUserRoles(user);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void setUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(r -> roleService.getRoleByName(r.getName()))
                .collect(Collectors.toSet()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setRoles(null);
            userRepository.delete(user);
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    // метод преобразует коллекцию Role в Authority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
