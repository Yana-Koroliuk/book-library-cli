package com.koroliuk.book_lib_cli.controller;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.SessionManager;
import com.koroliuk.book_lib_cli.model.Category;
import com.koroliuk.book_lib_cli.service.CategoryService;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;

public class CategoryControllerTest {
    private CategoryService categoryService;

    private CategoryController categoryController;

    @Before
    public void setUp() {
        categoryService = mock(CategoryService.class);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    public void testCreateCategory_WhenValidInput() {
        String input = "\"CategoryName\"";
        String categoryName = "CategoryName";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(categoryService.createCategory(categoryName)).thenReturn(new Category(1, categoryName));
        categoryController.createCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(categoryService).createCategory(categoryName);
        mockedStatic.close();
    }

    @Test
    public void testCreateCategory_WhenCategoryNameEmpty() {
        String input = "\"\"";
        categoryController.createCategory(input);
    }

    @Test
    public void testCreateCategory_WhenCurrentUserNull() {
        String input = "\"CategoryName\"";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        categoryController.createCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testCreateCategory_WhenCategoryNull() {
        String input = "\"CategoryName\"";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(categoryService.createCategory("CategoryName")).thenReturn(null);
        categoryController.createCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(categoryService).createCategory("CategoryName");
        mockedStatic.close();
    }

    @Test
    public void testUpdateCategory_WhenCategoryNameNotEmpty() {
        String input = "1 \"NewCategoryName\"";
        int categoryId = 1;
        String categoryNameNew = "NewCategoryName";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(categoryService.updateCategory(categoryId, categoryNameNew)).thenReturn(new Category(categoryId, categoryNameNew));
        categoryController.updateCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(categoryService).updateCategory(categoryId, categoryNameNew);
        mockedStatic.close();
    }

    @Test
    public void testUpdateCategory_WhenCategoryNameEmpty() {
        String input = "0 \"\"";
        categoryController.updateCategory(input);
    }

    @Test
    public void testUpdateCategory_WhenCurrentUserNull() {
        String input = "1 \"NewCategoryName\"";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        categoryController.updateCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testDeleteCategory_WhenCategoryExists() {
        String input = "1";
        int categoryId = 1;
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(categoryService.deleteCategoryById(categoryId)).thenReturn(true);
        categoryController.deleteCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(categoryService).deleteCategoryById(categoryId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteCategory_WhenCategoryNotExist() {
        int categoryId = 1;
        String input = "1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(categoryService.deleteCategoryById(categoryId)).thenReturn(false);
        categoryController.deleteCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(categoryService).deleteCategoryById(categoryId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteCategory_WhenCurrentUserNull() {
        String input = "1";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        categoryController.deleteCategory(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

}
