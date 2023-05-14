package com.koroliuk.book_lib_cli.controller;

import com.koroliuk.book_lib_cli.model.Book;
import com.koroliuk.book_lib_cli.service.*;

import java.util.*;

import static com.koroliuk.book_lib_cli.SessionManager.getCurrentUser;

public class BookController {
    static BookService bookService = new BookService();

    public static void createBook(String input) {
        String[] parts = input.split("\"");
        String bookName = parts[1];
        String categoryName = parts[3];
        String authorsString = input.substring(input.indexOf("[") + 1, input.indexOf("]"));
        List<String> authors = Arrays.asList(authorsString.split(", "));
        int exemplars = Integer.parseInt(parts[4].trim());
        if (!Objects.equals(bookName, "") && !authors.contains("") && !Objects.equals(categoryName, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                if (exemplars >= 0) {
                    int bookId = bookService.createBook(authors, bookName, categoryName, exemplars);
                    if (bookId != 0) {
                        System.out.println("Book id: " + bookId);
                    } else {
                        System.out.println("Already exist");
                    }
                } else {
                    System.out.println("Wrong exemplars");
                }
            } else {
                System.out.println("No possibility");
            }
        } else {
            System.out.println("Please write again");
        }
    }

    public static void updateBook(String input) {
        String[] parts = input.split("\"");
        int bookId = Integer.parseInt(parts[0].trim());
        String bookNameNew = parts[1];
        String categoryName = parts[3];
        String authorsString = input.substring(input.indexOf("[") + 1, input.indexOf("]"));
        List<String> authors = Arrays.asList(authorsString.split(", "));
        int exemplars = Integer.parseInt(parts[4].trim());
        if (!Objects.equals(bookNameNew, "") && !authors.contains("") && !Objects.equals(categoryName, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                if (exemplars >= 0) {
                    Book book = bookService.updateBook(bookId, authors, bookNameNew, categoryName, exemplars);
                    System.out.println("id | book name | category id | category name");
                    System.out.println("-------------------------------------------");
                    System.out.println(book.getId() + " | " + "\"" + book.getTitle() + "\"" + " | " + book.getCategoryId() + " | " + categoryName);
                } else {
                    System.out.println("Wrong exemplars");
                }
            } else {
                System.out.println("No possibility");
            }
        } else {
            System.out.println("Please write again");
        }
    }

    public static void deleteBook(String input) {
        int bookId = Integer.parseInt(input.trim());
        String currentUser = getCurrentUser();
        if (currentUser != null) {
            if (bookService.deleteBookById(bookId)) {
                System.out.println("Successful delete");
            } else {
                System.out.println("Not exist");
            }
        } else {
            System.out.println("No possibility");
        }
    }

    public static void statusBook(String input) {
        int bookId = Integer.parseInt(input.trim());
        int exemplars = bookService.findBookExemplars(bookId);
        System.out.println("Available " + exemplars + " exemplars");
    }

    public static void searchBook(String input) {
        List<String> parsedStrings = new ArrayList<>();
        int startIndex = 0;
        int endIndex;
        while (startIndex < input.length()) {
            if (input.charAt(startIndex) == '"') {
                endIndex = input.indexOf('"', startIndex + 1);
                String parsedString = input.substring(startIndex + 1, endIndex);
                parsedStrings.add(parsedString);
                startIndex = endIndex + 1;
            } else if (input.charAt(startIndex) == '[') {
                endIndex = input.indexOf(']', startIndex + 1);
                String parsedString = input.substring(startIndex + 1, endIndex);
                parsedStrings.add(parsedString);
                startIndex = endIndex + 1;
            } else {
                startIndex++;
            }
        }
        String bookName = parsedStrings.get(0);
        String categoryName = parsedStrings.get(2);
        String authorName = parsedStrings.get(1);
        bookName = !Objects.equals(bookName, "") ? bookName : null;
        categoryName = !Objects.equals(categoryName, "") ? categoryName : null;
        authorName = !Objects.equals(authorName, "") ? authorName : null;
        List<List<String>> books = bookService.searchBookByTitleAuthorCategory(bookName, authorName, categoryName);
        if (books.size() != 0) {
            System.out.println("id | book name | author name | category");
            System.out.println("---------------------------------------");
            for (List<String> book : books) {
                System.out.println(book.get(0) + " | " + book.get(1) + " | " + book.get(2) + " | " + book.get(3));
            }
        } else {
            System.out.println("Nothing found");
        }
    }
}
