package com.koroliuk.book_lib_cli.controller;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.SessionManager;
import com.koroliuk.book_lib_cli.service.UserService;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;

public class UserControllerTest {
    private UserService userService;

    private UserController userController;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void testUpdateUser_WhenUserNameEmpty() {
        String input = "0 \"\" ";
        userController.updateUser(input);
    }

    @Test
    public void testUpdateUser_WhenCurrentUserNull() {
        String input = "1 \"AuthorName\"";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        userController.updateUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testDeleteUser_WhenValidInput() {
        int userId = 1;
        String input = "1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(userService.deleteUserById(userId)).thenReturn(true);
        userController.deleteUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(userService).deleteUserById(userId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteUser_WhenCurrentUserNull() {
        String input = "1";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        userController.deleteUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testUpdateUser_WhenAuthorNotExist() {
        int authorId = 1;
        String input = "1";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(userService.deleteUserById(authorId)).thenReturn(false);
        userController.deleteUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(userService).deleteUserById(authorId);
        mockedStatic.close();
    }

    @Test
    public void testLogoutUser_WhenUserLoggedIn() {
        String input = "";
        String currentUser = "currentUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        userController.logoutUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testLogoutUser_WhenUserNotLoggedIn() {
        String input = "";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        userController.logoutUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }
}
