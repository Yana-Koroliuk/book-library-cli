package com.koroliuk.book_lib_cli.controller;

import com.koroliuk.book_lib_cli.model.Author;
import com.koroliuk.book_lib_cli.model.Book;
import com.koroliuk.book_lib_cli.service.AuthorService;

import java.util.Objects;

import static com.koroliuk.book_lib_cli.SessionManager.getCurrentUser;

public class AuthorController {
    static AuthorService authorService = new AuthorService();
    public static void createAuthor(String input) {
        String authorName = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
        if (!Objects.equals(authorName, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                int authorId = authorService.createAuthor(authorName);
                if (authorId != 0) {
                    System.out.println(authorId);
                } else {
                    System.out.println("Already exist");
                }
            } else {
                System.out.println("No possibility");
            }
        } else {
            System.out.println("Please write again");
        }
    }
    public static void updateAuthor(String input) {
        int authorId = Integer.parseInt(input.substring(0, input.indexOf(" ")));
        String authorNameNew = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
        if (authorId != 0 && !Objects.equals(authorNameNew, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                Author author = authorService.updateAuthor(authorId, authorNameNew);
                System.out.println(author.getId() + " " + author.getName());
            } else {
                System.out.println("No possibility");
            }
        } else {
            System.out.println("Please write again");
        }
    }
    public static void deleteAuthor(String input) {
        int authorId = Integer.parseInt(input.trim());
        String currentUser = getCurrentUser();
        if (currentUser != null) {
            if (authorService.deleteAuthorById(authorId)) {
                System.out.println("Successful delete");
            } else {
                System.out.println("Not exist");
            }
        } else {
            System.out.println("No possibility");
        }
    }
}
