package com.koroliuk.book_lib_cli.service;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryServiceTest {
    private CategoryDao categoryDao;
    private CategoryService categoryService;

    @Before
    public void setUp() {
        categoryDao = mock(CategoryDao.class);
        categoryService = new CategoryService(categoryDao);
    }

    @Test
    public void testCreateCategory_WhenCategoryNotExist() {
        int categoryId = 1;
        String categoryName = "Category 1";
        Category expectedCategory = new Category(categoryId, categoryName);
        when(categoryDao.existByName(categoryName)).thenReturn(false);
        when(categoryDao.createCategory(categoryName)).thenReturn(categoryId);
        Category actualCategory = categoryService.createCategory(categoryName);
        Assert.assertEquals(expectedCategory, actualCategory);
        verify(categoryDao).existByName(categoryName);
        verify(categoryDao).createCategory(categoryName);
        verifyNoMoreInteractions(categoryDao);
    }

    @Test
    public void testCreateCategory_WhenCategoryExist() {
        String categoryName = "Category 1";
        when(categoryDao.existByName(categoryName)).thenReturn(true);
        Category actualCategory = categoryService.createCategory(categoryName);
        Assert.assertNull(actualCategory);
        verify(categoryDao).existByName(categoryName);
        verifyNoMoreInteractions(categoryDao);
    }

    @Test
    public void testUpdateCategory_WhenCategoryExist() {
        int categoryId = 1;
        String categoryNameNew = "Category 1";
        Category expectedCategory = new Category(categoryId, categoryNameNew);
        when(categoryDao.existById(categoryId)).thenReturn(true);
        when(categoryDao.updateCategory(categoryId, categoryNameNew)).thenReturn(true);
        Category actualCategory = categoryService.updateCategory(categoryId, categoryNameNew);
        Assert.assertEquals(expectedCategory, actualCategory);
        verify(categoryDao).existById(categoryId);
        verify(categoryDao).updateCategory(categoryId, categoryNameNew);
        verifyNoMoreInteractions(categoryDao);
    }

    @Test
    public void testUpdateCategory_WhenCategoryNotExist() {
        int categoryId = 1;
        String categoryNameNew = "Category 1";
        when(categoryDao.existById(categoryId)).thenReturn(false);
        Category actualCategory = categoryService.updateCategory(categoryId, categoryNameNew);
        Assert.assertNull(actualCategory);
        verify(categoryDao).existById(categoryId);
        verifyNoMoreInteractions(categoryDao);
    }

    @Test
    public void testDeleteCategoryById_WhenCategoryExist() {
        int categoryId = 1;
        when(categoryDao.existById(categoryId)).thenReturn(true);
        when(categoryDao.deleteCategoryById(categoryId)).thenReturn(true);
        boolean actualCategory = categoryService.deleteCategoryById(categoryId);
        Assert.assertTrue(actualCategory);
        verify(categoryDao).existById(categoryId);
        verify(categoryDao).deleteCategoryById(categoryId);
        verifyNoMoreInteractions(categoryDao);
    }

    @Test
    public void testDeleteCategoryById_WhenCategoryNotExist() {
        int categoryId = 1;
        when(categoryDao.existById(categoryId)).thenReturn(false);
        boolean actualCategory = categoryService.deleteCategoryById(categoryId);
        Assert.assertFalse(actualCategory);
        verify(categoryDao).existById(categoryId);
        verifyNoMoreInteractions(categoryDao);
    }
}
