package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Category;

public class CategoryService {
    CategoryDao categoryDao = new CategoryDao();
    public int createCategory(String categoryName) {
        int categoryId = 0;
        if (!categoryDao.existCategory(categoryName)) {
            categoryId = categoryDao.createCategory(categoryName);
        }
        return categoryId;
    }
    public Category updateCategory(String categoryNameOld, String categoryNameNew) {
        Category category = null;
        if (categoryDao.existCategory(categoryNameOld)) {
            int categoryId = categoryDao.findByName(categoryNameOld);
            if (categoryDao.updateCategory(categoryNameNew, categoryId)) {
                category = new Category(categoryId, categoryNameNew);
            }
        }
        return category;
    }
    public Category deleteCategoryById(String categoryName) {
        Category category = null;
        if (categoryDao.existCategory(categoryName)) {
            int categoryId = categoryDao.findByName(categoryName);
            if (categoryDao.deleteCategoryById(categoryId)) {
                category = new Category(categoryId, categoryName);
            }
        }
        return category;
    }
}
