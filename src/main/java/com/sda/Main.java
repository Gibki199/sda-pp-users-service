package com.sda;


import com.sda.controller.InputController;
import com.sda.controller.UserController;
import com.sda.dao.UsersDAO;
import com.sda.mapper.UserMapper;
import com.sda.model.User;
import com.sda.service.UserService;

import java.util.Scanner;

public class Main {

    private final static UsersDAO usersDAO = new UsersDAO();
    private final static UserMapper userMapper = new UserMapper();
    private final static Scanner scanner = new Scanner(System.in);
    private final static InputController inputController = new InputController(scanner);
    private final static UserService userService = new UserService(usersDAO, userMapper);
    private final static UserController userController = new UserController(userService);

    public static void main(String[] args) {

        String option;

        do {
            System.out.println("""
                    Options:
                    1 - List users
                    2 - Find user
                    3 - Create user
                    4 - Delete user
                    5 - Update user
                    6 - Exit
                    """
            );
            option = inputController.getString("Please enter option: ");

            switch (option){
                case "1" -> userController.findAll();
                case "2" -> getUser();
                case "3" -> createUser();
                case "4" -> deleteUser();
                case "5" -> updateUser();
                case "6" -> System.out.println("Bye!");
                default -> System.out.println("Unsupported option");
            }

        } while (!"6".equalsIgnoreCase(option));

    }

    private static void getUser() {
        String username = inputController.getUsername();
        userController.findByUsername(username);
    }

    private static void createUser() {
        User user = User.builder()
                .username(inputController.getUsername())
                .name(inputController.getName())
                .surname(inputController.getSurname())
                .age(inputController.getAge())
                .email(inputController.getEmail())
                .password(inputController.getPassword())
                .build();

        userController.create(user);
    }

    private static void deleteUser() {
        String username = inputController.getUsername();
        userController.deleteByUsername(username);
    }

    private static void updateUser() {
        String username = inputController.getUsername();
        System.out.println("Update user info: ");
        User user = User.builder()
                .username(inputController.getUsername())
                .name(inputController.getName())
                .surname(inputController.getSurname())
                .age(inputController.getAge())
                .email(inputController.getEmail())
                .password(inputController.getPassword())
                .build();

        userController.update(user, username);
    }
}