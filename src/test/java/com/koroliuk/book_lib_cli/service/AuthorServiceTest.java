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
    public void testCreateAuthorNotExist() {
        String authorName = "John Doe";
        int expectedAuthorId = 1;
        when(authorDao.existByName(authorName)).thenReturn(false);
        when(authorDao.createAuthor(authorName)).thenReturn(expectedAuthorId);
        int actualAuthorId = authorService.createAuthor(authorName);
        Assert.assertEquals(expectedAuthorId, actualAuthorId);
        verify(authorDao).existByName(authorName);
        verify(authorDao).createAuthor(authorName);
    }
    @Test
    public void testCreateAuthorExist() {
        String authorName = "John Doe";
        int expectedAuthorId = 0;
        when(authorDao.existByName(authorName)).thenReturn(true);
        int actualAuthorId = authorService.createAuthor(authorName);
        Assert.assertEquals(expectedAuthorId, actualAuthorId);
        verify(authorDao).existByName(authorName);
        verify(authorDao, never()).createAuthor(authorName);
    }
    @Test
    public void testUpdateAuthorExist() {
        int authorId = 1;
        String authorNameNew = "Jane Doe";
        Author expectedAuthor = new Author(authorId, authorNameNew);
        when(authorDao.existById(authorId)).thenReturn(true);
        when(authorDao.updateAuthor(authorId, authorNameNew)).thenReturn(true);
        Author actualAuthor = authorService.updateAuthor(authorId, authorNameNew);
        Assert.assertEquals(expectedAuthor.getId(), actualAuthor.getId());
        Assert.assertEquals(expectedAuthor.getName(), actualAuthor.getName());
        verify(authorDao).existById(authorId);
        verify(authorDao).updateAuthor(authorId, authorNameNew);
    }
    @Test
    public void testUpdateAuthorNotExist() {
        int authorId = 1;
        String authorNameNew = "Jane Doe";
        Author expectedAuthor = null;
        when(authorDao.existById(authorId)).thenReturn(false);
        Author actualAuthor = authorService.updateAuthor(authorId, authorNameNew);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        verify(authorDao).existById(authorId);
        verify(authorDao, never()).updateAuthor(authorId, authorNameNew);
    }
    @Test
    public void testDeleteAuthorByIdExist() {
        int authorId = 1;
        when(authorDao.existById(authorId)).thenReturn(true);
        boolean result = authorService.deleteAuthorById(authorId);
        Assert.assertTrue(result);
        verify(authorDao).existById(authorId);
        verify(authorDao).deleteAuthorById(authorId);
    }
    @Test
    public void testDeleteAuthorByIdNotExist() {
        int authorId = 1;
        when(authorDao.existById(authorId)).thenReturn(false);
        boolean result = authorService.deleteAuthorById(authorId);
        Assert.assertFalse(result);
        verify(authorDao).existById(authorId);
        verify(authorDao, never()).deleteAuthorById(authorId);
    }
}
