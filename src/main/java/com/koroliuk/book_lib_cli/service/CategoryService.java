package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Category;

public class CategoryService {
    private static CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        CategoryService.categoryDao = categoryDao;
    }

    public Category createCategory(String categoryName) {
        Category category = null;
        if (!categoryDao.existByName(categoryName)) {
            int categoryId = categoryDao.createCategory(categoryName);
            category = new Category(categoryId, categoryName);
        }
        return category;
    }

    public Category updateCategory(int categoryId, String categoryNameNew) {
        Category category = null;
        if (categoryDao.existById(categoryId)) {
            if (categoryDao.updateCategory(categoryId, categoryNameNew)) {
                category = new Category(categoryId, categoryNameNew);
            }
        }
        return category;
    }

    public boolean deleteCategoryById(int categoryId) {
        if (categoryDao.existById(categoryId)) {
            categoryDao.deleteCategoryById(categoryId);
            return true;
        }
        return false;
    }
}
