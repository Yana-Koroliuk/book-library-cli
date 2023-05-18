package com.koroliuk.book_lib_cli.controller;


import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.SessionManager;
import com.koroliuk.book_lib_cli.model.Book;
import com.koroliuk.book_lib_cli.service.BookService;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookControllerTest {
    private BookService bookService;

    private BookController bookController;

    @Before
    public void setUp() {
        bookService = mock(BookService.class);
        bookController = new BookController(bookService);
    }

    @Test
    public void testCreateBook_WhenValidInput() {
        String input = "\"BookName\" [Author1, Author2] \"CategoryName\" 5";
        String currentUser = "currentUser";
        List<String> authors = Arrays.asList("Author1", "Author2");
        int exemplars = 5;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(bookService.createBook(authors, "BookName", "CategoryName", exemplars)).thenReturn(new Book(1, "BookName", 1, exemplars));
        bookController.createBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(bookService).createBook(authors, "BookName", "CategoryName", exemplars);
        mockedStatic.close();
    }

    @Test
    public void testCreateBook_WhenInputIsEmpty() {
        String input = "\"\" [] \"\" 0";
        bookController.createBook(input);
    }

    @Test
    public void testCreateBook_WhenCurrentUserNull() {
        String input = "\"BookName\" [Author1, Author2] \"CategoryName\" 5";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        bookController.createBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testCreateBook_WhenInvalidExemplars() {
        String input = "\"BookName\" [Author1, Author2] \"CategoryName\" -1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        bookController.createBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testCreateBook_WhenBookNull() {
        String input = "\"BookName\" [Author1, Author2] \"CategoryName\" 5";
        String currentUser = "currentUser";
        List<String> authors = Arrays.asList("Author1", "Author2");
        int exemplars = 5;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(bookService.createBook(authors, "BookName", "CatogoryName", exemplars)).thenReturn(null);
        bookController.createBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(bookService).createBook(authors, "BookName", "CategoryName", exemplars);
        mockedStatic.close();
    }

    @Test
    public void testUpdateBook_WhenValidInput() {
        String input = "1 \"NewBookName\" [Author1, Author2] \"CategoryName\" 5";
        String currentUser = "currentUser";
        int bookId = 1;
        List<String> authors = Arrays.asList("Author1", "Author2");
        int exemplars = 5;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(bookService.updateBook(bookId, authors, "NewBookName", "CategoryName", exemplars)).thenReturn(new Book(1, "NewBookName", 1, exemplars));
        bookController.updateBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(bookService).updateBook(bookId, authors, "NewBookName", "CategoryName", exemplars);
        mockedStatic.close();
    }

    @Test
    public void testUpdateBook_WhenInvalidExemplars() {
        String input = "1 \"BookName\" [Author1, Author2] \"CategoryName\" -1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        bookController.updateBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testUpdateBook_WhenInputIsEmpty() {
        String input = "0 \"\" [] \"\" 0";
        bookController.updateBook(input);
    }

    @Test
    public void testUpdateBook_WhenCurrentUserNull() {
        String input = "1 \"BookName\" [Author1, Author2] \"CategoryName\" 5";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        bookController.updateBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testDeleteBook_WhenValidInput() {
        String input = "1";
        String currentUser = "currentUser";
        int bookId = 1;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(bookService.deleteBookById(bookId)).thenReturn(true);
        bookController.deleteBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(bookService).deleteBookById(bookId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteBook_WhenBookNotExist() {
        String input = "1";
        String currentUser = "currentUser";
        int bookId = 1;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(bookService.deleteBookById(bookId)).thenReturn(false);
        bookController.deleteBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(bookService).deleteBookById(bookId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteBook_WhenCurrentUserNull() {
        String input = "1";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        bookController.deleteBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testStatusBook_WhenValidBookId() {
        String input = "1";
        int bookId = 1;
        int exemplars = 5;
        when(bookService.findBookExemplars(bookId)).thenReturn(exemplars);
        bookController.statusBook(input);
        verify(bookService).findBookExemplars(bookId);
    }

    @Test
    public void testSearchBook_WhenMatchingBooksFound() {
        String input = "\"BookName\" [AuthorName] \"CategoryName\"";
        String bookName = "BookName";
        String authorName = "AuthorName";
        String categoryName = "CategoryName";
        List<List<String>> books = new ArrayList<>();
        List<String> book1 = Arrays.asList("1", "Book1", "Author1", "Category1");
        List<String> book2 = Arrays.asList("2", "Book2", "Author2", "Category2");
        books.add(book1);
        books.add(book2);
        when(bookService.searchBookByTitleAuthorCategory(bookName, authorName, categoryName)).thenReturn(books);
        bookController.searchBook(input);
        verify(bookService).searchBookByTitleAuthorCategory(bookName, authorName, categoryName);
    }

    @Test
    public void testSearchBook_WhenMatchingBooksNotFound() {
        String input = "\"BookName\" [AuthorName] \"CategoryName\"";
        String bookName = "BookName";
        String authorName = "AuthorName";
        String categoryName = "CategoryName";
        List<List<String>> books = new ArrayList<>();
        when(bookService.searchBookByTitleAuthorCategory(bookName, authorName, categoryName)).thenReturn(books);
        bookController.searchBook(input);
        verify(bookService).searchBookByTitleAuthorCategory(bookName, authorName, categoryName);
    }
}
