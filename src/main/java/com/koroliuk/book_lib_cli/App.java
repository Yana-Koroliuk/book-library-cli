package com.koroliuk.book_lib_cli;

import com.koroliuk.book_lib_cli.controller.*;

import java.util.*;
import java.util.function.Consumer;

public class App {
    public static void main(String[] args) {
        Map<String, Consumer<String>> map = Map.ofEntries(
                Map.entry("create-book", BookController::createBook),
                Map.entry("create-author", AuthorController::createAuthor),
                Map.entry("create-category", CategoryController::createCategory),
                Map.entry("update-book", BookController::updateBook),
                Map.entry("update-author", AuthorController::updateAuthor),
                Map.entry("update-category", CategoryController::updateCategory),
                Map.entry("update-user", UserController::updateUser),
                Map.entry("delete-book", BookController::deleteBook),
                Map.entry("delete-author", AuthorController::deleteAuthor),
                Map.entry("delete-category", CategoryController::deleteCategory),
                Map.entry("delete-user", UserController::deleteUser),
                Map.entry("delete-order", OrderController::deleteOder),
                Map.entry("status-book", BookController::statusBook),
                Map.entry("status-user", OrderController::checkOrdersOfUser),
                Map.entry("order-book", OrderController::orderBook),
                Map.entry("return-book", OrderController::returnBook),
                Map.entry("search", BookController::searchBook),
                Map.entry("login", UserController::loginUser),
                Map.entry("logout", UserController::logoutUser),
                Map.entry("register", UserController::registerUser)
        );
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] inputs = input.split(" ");
            String command = inputs[0];
            try {
                if ("quit".equals(command)) {
                    break;
                } else {
                    map.get(command).accept(input.substring(input.indexOf(" ") + 1));
                }
            } catch (Exception e) {
                System.out.println("Please write again!");
            }
        }
    }
}