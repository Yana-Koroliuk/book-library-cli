package com.koroliuk.book_lib_cli.controller;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.SessionManager;
import com.koroliuk.book_lib_cli.model.Author;
import com.koroliuk.book_lib_cli.service.AuthorService;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;

public class AuthorControllerTest {
    private AuthorService authorService;

    private AuthorController authorController;

    @Before
    public void setUp() {
        authorService = mock(AuthorService.class);
        authorController = new AuthorController(authorService);
    }

    @Test
    public void testCreateAuthor_WhenValidInput() {
        String input = "\"AuthorName\"";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(authorService.createAuthor("AuthorName")).thenReturn(new Author(1, "AuthorName"));
        authorController.createAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(authorService).createAuthor("AuthorName");
        mockedStatic.close();
    }

    @Test
    public void testCreateAuthor_WhenAuthorNameEmpty() {
        String input = "\"\"";
        authorController.createAuthor(input);
    }

    @Test
    public void testCreateAuthor_WhenCurrentUserNull() {
        String input = "\"AuthorName\"";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        authorController.createAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testCreateAuthor_WhenAuthorNull() {
        String input = "\"AuthorName\"";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(authorService.createAuthor("AuthorName")).thenReturn(null);
        authorController.createAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(authorService).createAuthor("AuthorName");
        mockedStatic.close();
    }

    @Test
    public void testUpdateAuthor_WhenValidInput() {
        int authorId = 1;
        String authorNameNew = "NewAuthorName";
        String input = authorId + " \"NewAuthorName\"";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(authorService.updateAuthor(authorId, authorNameNew)).thenReturn(new Author(authorId, authorNameNew));
        authorController.updateAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(authorService).updateAuthor(authorId, authorNameNew);
        mockedStatic.close();

    }

    @Test
    public void testUpdateAuthor_WhenAuthorNameEmpty() {
        String input = "0 \"\"";
        authorController.updateAuthor(input);
    }

    @Test
    public void testUpdateAuthor_WhenCurrentUserNull() {
        String input = "1 \"AuthorName\"";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        authorController.updateAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testDeleteAuthor_WhenValidInput() {
        int authorId = 1;
        String input = "1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(authorService.deleteAuthorById(authorId)).thenReturn(true);
        authorController.deleteAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(authorService).deleteAuthorById(authorId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteAuthor_WhenCurrentUserNull() {
        String input = "1";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        authorController.deleteAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testUpdateAuthor_WhenAuthorNotExist() {
        int authorId = 1;
        String input = "1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(authorService.deleteAuthorById(authorId)).thenReturn(false);
        authorController.deleteAuthor(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(authorService).deleteAuthorById(authorId);
        mockedStatic.close();
    }
}
