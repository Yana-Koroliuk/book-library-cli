package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Category;

public class CategoryService {
    CategoryDao categoryDao = new CategoryDao();

    public int createCategory(String categoryName) {
        int categoryId = 0;
        if (!categoryDao.existByName(categoryName)) {
            categoryId = categoryDao.createCategory(categoryName);
        }
        return categoryId;
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
