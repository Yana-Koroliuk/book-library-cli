package com.koroliuk.book_lib_cli.service;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.dao.BookAuthorDao;
import com.koroliuk.book_lib_cli.dao.BookDao;
import com.koroliuk.book_lib_cli.dao.CategoryDao;
import com.koroliuk.book_lib_cli.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookServiceTest {
    private BookDao bookDao;
    private CategoryDao categoryDao;
    private AuthorDao authorDao;
    private BookAuthorDao bookAuthorDao;
    private BookService bookService;

    @Before
    public void setUp() {
        bookDao = mock(BookDao.class);
        categoryDao = mock(CategoryDao.class);
        authorDao = mock(AuthorDao.class);
        bookAuthorDao = mock(BookAuthorDao.class);
        bookService = new BookService(bookDao, authorDao, categoryDao, bookAuthorDao);
    }

    @Test
    public void testCreateBook_WhenBookNotExist() {
        List<String> authors = Arrays.asList("Author 1", "Author 2");
        String bookName = "Book 1";
        String category = "Category 1";
        int exemplars = 2;
        int categoryId = 1;
        int bookId = 1;
        Book expectedBook = new Book(bookId, bookName, categoryId, exemplars);
        when(bookDao.existBook(bookName)).thenReturn(false);
        when(categoryDao.existByName(category)).thenReturn(false);
        when(categoryDao.createCategory(category)).thenReturn(1);
        when(bookDao.createBook(bookName, 1, exemplars)).thenReturn(1);
        when(authorDao.existByName("Author 1")).thenReturn(false);
        when(authorDao.createAuthor("Author 1")).thenReturn(1);
        when(authorDao.existByName("Author 2")).thenReturn(false);
        when(authorDao.createAuthor("Author 2")).thenReturn(2);
        when(bookAuthorDao.existBookAuthor(1, 1)).thenReturn(false);
        when(bookAuthorDao.existBookAuthor(1, 2)).thenReturn(false);
        Book actualBook = bookService.createBook(authors, bookName, category, exemplars);
        assertEquals(expectedBook, actualBook);
        verify(bookDao).existBook(bookName);
        verify(categoryDao).existByName(category);
        verify(categoryDao).createCategory(category);
        verify(bookDao).createBook(bookName, 1, exemplars);
        verify(authorDao).existByName("Author 1");
        verify(authorDao).createAuthor("Author 1");
        verify(authorDao).existByName("Author 2");
        verify(authorDao).createAuthor("Author 2");
        verify(bookAuthorDao).existBookAuthor(1, 1);
        verify(bookAuthorDao).existBookAuthor(1, 2);
        verify(bookAuthorDao).createBookAuthor(1, 1);
        verify(bookAuthorDao).createBookAuthor(1, 2);
        verifyNoMoreInteractions(bookDao, categoryDao, authorDao, bookAuthorDao);
    }

    @Test
    public void testCreateBook_WhenBookExists() {
        List<String> authors = Arrays.asList("Author 1", "Author 2");
        String bookName = "Book 1";
        String category = "Category 1";
        int exemplars = 2;
        when(bookDao.existBook(bookName)).thenReturn(true);
        Book actualBook = bookService.createBook(authors, bookName, category, exemplars);
        assertNull(actualBook);
        verify(bookDao).existBook(bookName);
        verifyNoMoreInteractions(bookDao, categoryDao, authorDao, bookAuthorDao);
    }

    @Test
    public void testUpdateBook_WhenBookExists() {
        int bookId = 1;
        List<String> authors = Arrays.asList("Author 1", "Author 2");
        String bookNameNew = "New Book";
        String category = "Category 1";
        int exemplars = 2;
        Book expectedBook = new Book(bookId, bookNameNew, 1, exemplars);
        when(bookDao.existBookById(bookId)).thenReturn(true);
        when(categoryDao.existByName(category)).thenReturn(false);
        when(categoryDao.createCategory(category)).thenReturn(1);
        when(bookDao.updateBook(bookId, bookNameNew, 1, exemplars)).thenReturn(true);
        when(bookAuthorDao.findByBookId(bookId)).thenReturn(List.of(1, 2));
        when(bookAuthorDao.isAuthorUsedInOtherBooks(1, bookId)).thenReturn(false);
        when(authorDao.existByName("Author 1")).thenReturn(false);
        when(authorDao.createAuthor("Author 1")).thenReturn(1);
        when(authorDao.existByName("Author 2")).thenReturn(false);
        when(authorDao.createAuthor("Author 2")).thenReturn(2);
        Book actualBook = bookService.updateBook(bookId, authors, bookNameNew, category, exemplars);
        assertEquals(expectedBook, actualBook);
        verify(bookDao).existBookById(bookId);
        verify(categoryDao).existByName(category);
        verify(categoryDao).createCategory(category);
        verify(bookDao).updateBook(bookId, bookNameNew, 1, exemplars);
        verify(bookAuthorDao).findByBookId(bookId);
        verify(bookAuthorDao).isAuthorUsedInOtherBooks(1, bookId);
        verify(bookAuthorDao).isAuthorUsedInOtherBooks(2, bookId);
        verify(authorDao).deleteAuthorById(1);
        verify(authorDao).deleteAuthorById(2);
        verify(authorDao).existByName("Author 1");
        verify(authorDao).existByName("Author 2");
        verify(authorDao).createAuthor("Author 1");
        verify(authorDao).createAuthor("Author 2");
        verify(bookAuthorDao).createBookAuthor(bookId, 1);
        verify(bookAuthorDao).createBookAuthor(bookId, 2);
        verifyNoMoreInteractions(bookDao, categoryDao, authorDao, bookAuthorDao);
    }

    @Test
    public void testUpdateBook_WhenBookDoesNotExist() {
        int bookId = 1;
        List<String> authors = Arrays.asList("Author 1", "Author 2");
        String bookNameNew = "Updated Book";
        String category = "Category 1";
        int exemplars = 2;
        when(bookDao.existBookById(bookId)).thenReturn(false);
        Book actualBook = bookService.updateBook(bookId, authors, bookNameNew, category, exemplars);
        assertNull(actualBook);
        verify(bookDao).existBookById(bookId);
        verifyNoMoreInteractions(bookDao, categoryDao, authorDao, bookAuthorDao);
    }

    @Test
    public void testDeleteBookById_WhenBookExists() {
        int bookId = 1;
        List<Integer> authorsId = Arrays.asList(1, 2);
        when(bookAuthorDao.findByBookId(bookId)).thenReturn(authorsId);
        when(bookAuthorDao.isAuthorUsedInOtherBooks(1, bookId)).thenReturn(true);
        when(bookAuthorDao.isAuthorUsedInOtherBooks(2, bookId)).thenReturn(false);
        when(bookAuthorDao.deleteBookAuthorByBookAuthorId(bookId, 1)).thenReturn(true);
        when(authorDao.deleteAuthorById(2)).thenReturn(true);
        when(bookDao.existBookById(bookId)).thenReturn(true);
        boolean actualResult = bookService.deleteBookById(bookId);
        Assert.assertTrue(actualResult);
        verify(bookAuthorDao).findByBookId(bookId);
        verify(bookAuthorDao).isAuthorUsedInOtherBooks(1, bookId);
        verify(authorDao, never()).deleteAuthorById(1);
        verify(bookAuthorDao).deleteBookAuthorByBookAuthorId(bookId, 1);
        verify(bookAuthorDao).isAuthorUsedInOtherBooks(2, bookId);
        verify(authorDao).deleteAuthorById(2);
        verify(bookDao).existBookById(bookId);
        verify(bookDao).deleteBookById(bookId);
        verifyNoMoreInteractions(bookDao, categoryDao, authorDao, bookAuthorDao);
    }

    @Test
    public void testDeleteBookById_WhenBookNotExists() {
        int bookId = 1;
        when(bookDao.existBookById(bookId)).thenReturn(false);
        boolean actualResult = bookService.deleteBookById(bookId);
        Assert.assertFalse(actualResult);
        verify(bookDao).existBookById(bookId);
        verifyNoMoreInteractions(bookDao);
    }

    @Test
    public void testSearchBookByTitleAuthorCategory() {
        String title = "Book 1";
        String author = "Author 1";
        String category = "Category 1";
        List<List<String>> expectedBooks = new ArrayList<>();
        when(bookDao.findBookByTitleAuthorCategory(title, author, category)).thenReturn(expectedBooks);
        List<List<String>> actualBooks = bookService.searchBookByTitleAuthorCategory(title, author, category);
        assertEquals(expectedBooks, actualBooks);
        verify(bookDao).findBookByTitleAuthorCategory(title, author, category);
        verifyNoMoreInteractions(bookDao, categoryDao, authorDao, bookAuthorDao);
    }

    @Test
    public void testFindBookExemplars_WhenBookExist() {
        int bookId = 1;
        int expectedExemplars = 2;
        when(bookDao.existBookById(bookId)).thenReturn(true);
        when(bookDao.findExemplarsByBookId(bookId)).thenReturn(expectedExemplars);
        int actualExemplars = bookService.findBookExemplars(bookId);
        Assert.assertEquals(expectedExemplars, actualExemplars);
        verify(bookDao).existBookById(bookId);
        verify(bookDao).findExemplarsByBookId(bookId);
        verifyNoMoreInteractions(bookDao);
    }

    @Test
    public void testFindBookExemplars_WhenBookNotExists() {
        int bookId = 1;
        int expectedExemplars = 0;
        when(bookDao.existBookById(bookId)).thenReturn(false);
        int actualExemplars = bookService.findBookExemplars(bookId);
        Assert.assertEquals(expectedExemplars, actualExemplars);
        verify(bookDao).existBookById(bookId);
        verifyNoMoreInteractions(bookDao);
    }
}
