package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.HashSet;

public class UserDto {
    private Long id;

    private String username;

    private String name;

    private String lastName;

    private HashSet<String> roles;

    public UserDto() {

    }

    public UserDto(Long id, String username, String name, String lastName) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
    }

    public UserDto(Long id, String username, String name, String lastName, HashSet<String> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<String> roles) {
        this.roles = roles;
    }
    public void addRole(String role) {
        if (roles == null) {
            roles = new HashSet<>();
            roles.add(role);
        } else {
            roles.add(role);
        }
    }
}
