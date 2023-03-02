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
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll(); //  мб за  даункастить (List<User>)
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
//        Role roleByName = roleService.getRoleByName(role);
//        if (roleByName == null) {
//            throw new RuntimeException("Такая роль не существует ");
//        }
        user.setRoles(Collections.singleton(new Role(role)));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Long id, User updateUser) {
        User user = userRepository.findById(id).get();
        user.setName(updateUser.getName());
        user.setLastName(updateUser.getLastName());
        user.setRoles(updateUser.getRoles());
        userRepository.save(user);
    }
//        // Если пароль не изменяется, то не кодируем при обновлении
//        if (user.getPassword().equals(user.getPassword())) {
//            userRepository.save(user);
//        } else {
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            userRepository.save(user);
//        }


    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    // дают пользователя и по этому имени вернуть самого юзера loadUserByUsername
    @Override
    @Transactional
//    Транзакшинал мб не надо
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        User userOptional = user.get();
        // возвращаем только данные для Security
        return new org.springframework.security.core.userdetails.User(userOptional.getUsername(),
                userOptional.getPassword(), mapRolesToAuthorities(userOptional.getRoles()));
    }

    // метод преобразует коллекцию Role в Authority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
