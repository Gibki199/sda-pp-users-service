package com.sda.mapper;

import com.sda.dto.UserDTO;
import com.sda.model.User;

public class UserMapper {
    public UserDTO map(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }
}
