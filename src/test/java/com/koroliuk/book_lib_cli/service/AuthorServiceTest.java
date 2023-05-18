package com.koroliuk.book_lib_cli.service;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.model.Author;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthorServiceTest {
    private AuthorDao authorDao;
    private AuthorService authorService;

    @Before
    public void setUp() {
        authorDao = mock(AuthorDao.class);
        authorService = new AuthorService(authorDao);
    }

    @Test
    public void testCreateAuthor_WhenAuthorNotExist() {
        int authorId = 1;
        String authorName = "Jane Doe";
        Author expectedAuthor = new Author(authorId, authorName);
        when(authorDao.existByName(authorName)).thenReturn(false);
        when(authorDao.createAuthor(authorName)).thenReturn(expectedAuthor.getId());
        Author actualAuthor = authorService.createAuthor(authorName);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        verify(authorDao).existByName(authorName);
        verify(authorDao).createAuthor(authorName);
    }

    @Test
    public void testCreateAuthor_WhenAuthorExist() {
        String authorName = "Jane Doe";
        when(authorDao.existByName(authorName)).thenReturn(true);
        Author actualAuthor = authorService.createAuthor(authorName);
        Assert.assertNull(actualAuthor);
        verify(authorDao).existByName(authorName);
        verify(authorDao, never()).createAuthor(authorName);
    }

    @Test
    public void testUpdateAuthor_WhenAuthorExist() {
        int authorId = 1;
        String authorNameNew = "Jane Doe";
        Author expectedAuthor = new Author(authorId, authorNameNew);
        when(authorDao.existById(authorId)).thenReturn(true);
        when(authorDao.updateAuthor(authorId, authorNameNew)).thenReturn(true);
        Author actualAuthor = authorService.updateAuthor(authorId, authorNameNew);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        verify(authorDao).existById(authorId);
        verify(authorDao).updateAuthor(authorId, authorNameNew);
    }

    @Test
    public void testUpdateAuthor_WhenAuthorNotExist() {
        int authorId = 1;
        String authorNameNew = "Jane Doe";
        when(authorDao.existById(authorId)).thenReturn(false);
        Author actualAuthor = authorService.updateAuthor(authorId, authorNameNew);
        Assert.assertNull(actualAuthor);
        verify(authorDao).existById(authorId);
        verify(authorDao, never()).updateAuthor(authorId, authorNameNew);
    }

    @Test
    public void testDeleteAuthorById_WhenAuthorExist() {
        int authorId = 1;
        when(authorDao.existById(authorId)).thenReturn(true);
        boolean actualResult = authorService.deleteAuthorById(authorId);
        Assert.assertTrue(actualResult);
        verify(authorDao).existById(authorId);
        verify(authorDao).deleteAuthorById(authorId);
    }

    @Test
    public void testDeleteAuthorById_WhenAuthorNotExist() {
        int authorId = 1;
        when(authorDao.existById(authorId)).thenReturn(false);
        boolean actualResult = authorService.deleteAuthorById(authorId);
        Assert.assertFalse(actualResult);
        verify(authorDao).existById(authorId);
        verify(authorDao, never()).deleteAuthorById(authorId);
    }
}
