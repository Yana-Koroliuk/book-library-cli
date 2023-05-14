package com.koroliuk.book_lib_cli.controller;

import com.koroliuk.book_lib_cli.model.Category;
import com.koroliuk.book_lib_cli.service.CategoryService;

import java.util.Objects;

import static com.koroliuk.book_lib_cli.SessionManager.getCurrentUser;

public class CategoryController {
    static CategoryService categoryService = new CategoryService();

    public static void createCategory(String input) {
        String categoryName = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
        if (!Objects.equals(categoryName, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                int categoryId = categoryService.createCategory(categoryName);
                if (categoryId != 0) {
                    System.out.println("Category id: " + categoryId);
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

    public static void updateCategory(String input) {
        int categoryId = Integer.parseInt(input.substring(0, input.indexOf(" ")));
        String categoryNameNew = input.substring(input.indexOf("\"") + 1, input.lastIndexOf("\""));
        if (categoryId != 0 && !Objects.equals(categoryNameNew, "")) {
            String currentUser = getCurrentUser();
            if (currentUser != null) {
                Category category = categoryService.updateCategory(categoryId, categoryNameNew);
                System.out.println("id | category name");
                System.out.println("------------------");
                System.out.println(category.getId() + " | " + category.getName());
            } else {
                System.out.println("No possibility");
            }
        } else {
            System.out.println("Please write again");
        }
    }

    public static void deleteCategory(String input) {
        int categoryId = Integer.parseInt(input.trim());
        String currentUser = getCurrentUser();
        if (currentUser != null) {
            if (categoryService.deleteCategoryById(categoryId)) {
                System.out.println("Successful delete");
            } else {
                System.out.println("Not exist");
            }
        } else {
            System.out.println("No possibility");
        }
    }
}
