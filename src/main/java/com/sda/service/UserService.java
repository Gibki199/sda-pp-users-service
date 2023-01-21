package com.sda.service;

import com.sda.dao.UsersDAO;
import com.sda.dto.UserDTO;
import com.sda.exception.NotFoundException;
import com.sda.exception.UsernameConflictException;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
    private final UsersDAO userDAO;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userDAO.findAll().stream()
                .map(user -> userMapper.map(user))
                .collect(Collectors.toList());
    }
    
    public UserDTO findByUsername(String username) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("User with username %s not found".formatted(username));
        }
        return userMapper.map(user);
    }

    public void deleteByUsername(String username) {
        boolean result = userDAO.deleteByUsername(username);
        if (!result) {
            throw new NotFoundException("User with username %s not found!".formatted(username));
        }
    }

    public void create(User user) {
        if (userDAO.existsByUsername(user.getUsername())) {
            throw new UsernameConflictException("User already exist");
        }
        userDAO.create(user);
    }

    public UserDTO update(User user, String username) {
        if (!user.getUsername().equals(username)) {
            throw new UsernameConflictException("Usernames does not matching");
        }

        if (!userDAO.existsByUsername(username)) {
            throw new NotFoundException("User not exist");
        }
        User result = userDAO.update(user);
        return userMapper.map(result);
    }
}
