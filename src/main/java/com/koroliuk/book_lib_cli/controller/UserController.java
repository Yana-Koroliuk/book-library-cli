package com.koroliuk.book_lib_cli.controller;

import com.koroliuk.book_lib_cli.model.Category;
import com.koroliuk.book_lib_cli.model.User;
import com.koroliuk.book_lib_cli.service.UserService;

import java.util.Objects;
import java.util.Scanner;

import static com.koroliuk.book_lib_cli.PasswordHasher.hashPassword;
import static com.koroliuk.book_lib_cli.SessionManager.getCurrentUser;
import static com.koroliuk.book_lib_cli.SessionManager.setCurrentUser;

public class UserController {
    static UserService userService = new UserService();
    public static void updateUser(String input) {
        int userId = Integer.parseInt(input.substring(0, input.indexOf(" ")));
        String UserNameNew = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
        String password = input.substring(input.lastIndexOf(" ") + 1);
        if (userId != 0 && !Objects.equals(UserNameNew, "") && !Objects.equals(password, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                if (password.length() > 6) {
                    String hashedPassword = hashPassword(password);
                    User user = userService.updateUser(userId, UserNameNew, hashedPassword);
                    System.out.println(user.getId() + " " + user.getName());
                } else {
                    System.out.println("Password need to have more than 6 symbols");
                }
            } else {
                System.out.println("No possibility");
            }
        } else {
            System.out.println("Please write again");
        }
    }
    public static void deleteUser(String input) {
        int userId = Integer.parseInt(input.trim());
        String currentUser = getCurrentUser();
        if (currentUser != null) {
            if (userService.deleteUserById(userId)) {
                System.out.println("Successful delete");
            } else {
                System.out.println("Not exist");
            }
        } else {
            System.out.println("No possibility");
        }
    }
    public static void loginUser(String input) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("User name: ");
        String userName = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        if (userService.signInVerify(userName, password)) {
            setCurrentUser(userName);
            System.out.println("Successful login");
        } else {
            System.out.println("Wrong password");
        }
    }
    public static void logoutUser(String input) {
        if (getCurrentUser() != null) {
            setCurrentUser(null);
            System.out.println("Successful logout");
        } else {
            System.out.println("You need to login to logout");
        }
    }
    public static void registerUser(String input) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("User name: ");
        String userName = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        if (password.length() > 6) {
            String hashedPassword = hashPassword(password);
            int userId = userService.createUser(userName, hashedPassword);
            if (userId != 0) {
                System.out.println("User id: " + userId);
            } else {
                System.out.println("Already exist");
            }
        } else {
            System.out.println("Password need to have more than 6 symbols");
        }
    }
}
