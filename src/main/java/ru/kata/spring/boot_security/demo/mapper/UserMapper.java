package ru.kata.spring.boot_security.demo.mapper;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDto mapModelToDTO(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        user.getRoles().forEach(s -> userDto.addRole(s.getName()));
        return userDto;
    }

    public static User mapUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setId(userDto.getId());
        user.setLastName(userDto.getLastName());
        userDto.getRoles().forEach(s -> user.addRole(new Role(s)));
        return user;
    }

    public static List<UserDto> mapListModelToListDTO(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        userList.stream().forEach(z -> {
            UserDto userDto = mapModelToDTO(z);
            userDtoList.add(userDto);
        });
        return userDtoList;
    }
}
