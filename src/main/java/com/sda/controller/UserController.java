package com.sda.controller;

import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.model.User;
import com.sda.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public void findAll() {
        List<UserDTO> result = userService.findAll();
        if (result.isEmpty()) {
            System.out.println("Users list empty");
        } else {
            System.out.println("Users list:");
            result.forEach(System.out::println);
        }
    }

    public void findByUsername(String username) {
        try {
            UserDTO user = userService.findByUsername(username);
            System.out.println("User found:" + user);
        } catch (NotFoundException e) {
            log.error("NotFoundException: {}", e.getMessage());
        }
    }

    public void deleteByUsername(String username) {
        try {
            userService.deleteByUsername(username);
            System.out.printf("User with username %s deleted%n", username);
        } catch (NotFoundException e) {
            log.error("NotFoundException: {}", e.getMessage());
        }
    }

    public void create(User user) {
        try {
            userService.create(user);
            System.out.printf("User %s created%n", user.getUsername());
        } catch (UsernameConflictException e) {
            log.error("UsernameConflictException: {}", e.getMessage());
        }
    }

    public void update(User user, String username) {
        try {
            userService.update(user, username);
            System.out.printf("User with username %s updated!", username);
        } catch (UsernameConflictException e) {
            log.error("UsernameConflictException: {}", e.getMessage());
        } catch (NotFoundException e) {
            log.error("NotFoundException: {}", e.getMessage());
        }
    }
}
