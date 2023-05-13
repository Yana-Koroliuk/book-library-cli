package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Category;
import com.koroliuk.book_lib_cli.model.User;

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
    public Category deleteCategoryById(int categoryId) {
        Category category = null;
        if (categoryDao.existById(categoryId)) {
            category = categoryDao.readCategoryById(categoryId);
            categoryDao.deleteCategoryById(categoryId);
        }
        return category;
    }
}
