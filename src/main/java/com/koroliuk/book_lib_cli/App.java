package com.koroliuk.book_lib_cli;

import com.koroliuk.book_lib_cli.controller.*;
import com.koroliuk.book_lib_cli.dao.*;
import com.koroliuk.book_lib_cli.service.*;

import java.util.*;
import java.util.function.Consumer;

public class App {
    public static void main(String[] args) {
        AuthorDao authorDao = new AuthorDao();
        BookDao bookDao = new BookDao();
        CategoryDao categoryDao = new CategoryDao();
        OrderDao orderDao = new OrderDao();
        UserDao userDao = new UserDao();
        BookAuthorDao bookAuthorDao = new BookAuthorDao();

        AuthorService authorService = new AuthorService(authorDao);
        BookService bookService = new BookService(bookDao, authorDao, categoryDao, bookAuthorDao);
        CategoryService categoryService = new CategoryService(categoryDao);
        OrderService orderService = new OrderService(orderDao, bookDao, userDao);
        UserService userService = new UserService(userDao);

        AuthorController authorController = new AuthorController(authorService);
        BookController bookController = new BookController(bookService);
        CategoryController categoryController = new CategoryController(categoryService);
        OrderController orderController = new OrderController(orderService);
        UserController userController = new UserController(userService);

        Map<String, Consumer<String>> map = new HashMap<>();
        map.put("create-author", authorController::createAuthor);
        map.put("update-author", authorController::updateAuthor);
        map.put("delete-author", authorController::deleteAuthor);
        map.put("create-book", bookController::createBook);
        map.put("update-book", bookController::updateBook);
        map.put("delete-book", bookController::deleteBook);
        map.put("status-book", bookController::statusBook);
        map.put("search", bookController::searchBook);
        map.put("create-category", categoryController::createCategory);
        map.put("update-category", categoryController::updateCategory);
        map.put("delete-category", categoryController::deleteCategory);
        map.put("order-book", orderController::orderBook);
        map.put("return-book", orderController::returnBook);
        map.put("delete-order", orderController::deleteOder);
        map.put("status-user", orderController::checkOrdersOfUser);
        map.put("update-user", userController::updateUser);
        map.put("delete-user", userController::deleteUser);
        map.put("login", userController::loginUser);
        map.put("logout", userController::logoutUser);
        map.put("register", userController::registerUser);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(" ");
            String command = inputs[0];
            try {
                if ("quit".equals(command)) {
                    break;
                } else {
                    map.getOrDefault(command, (str) -> System.out.println("Invalid command")).accept(input.substring(input.indexOf(" ") + 1));
                }
            } catch (Exception e) {
                System.out.println("Please write again!");
            }
        }
    }
}