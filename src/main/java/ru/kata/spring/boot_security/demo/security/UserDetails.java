package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final User user;

    public UserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// здесь будет распределение мол кто че может делать
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    // нужно чтобы получать данные аутентифицированного пользователя
    public User getUser(){
        return this.user;
    }
}
