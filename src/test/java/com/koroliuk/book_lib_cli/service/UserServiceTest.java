package com.koroliuk.book_lib_cli.service;

import static com.koroliuk.book_lib_cli.PasswordHasher.verifyPassword;
import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.dao.*;
import com.koroliuk.book_lib_cli.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class UserServiceTest {
    private UserDao userDao;
    private UserService userService;

    @Before
    public void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }
    @Test
    public void testCreateUser_WhenUserNotExist() {
        String userName = "John";
        String password = "password";
        int userId = 1;
        User expectedUser = new User(userId, userName, password);
        when(userDao.existByName(userName)).thenReturn(false);
        when(userDao.createUser(userName, password)).thenReturn(userId);
        User actualUser = userService.createUser(userName, password);
        Assert.assertEquals(expectedUser, actualUser);
        verify(userDao).existByName(userName);
        verify(userDao).createUser(userName, password);
        verifyNoMoreInteractions(userDao);
    }
    @Test
    public void testCreateUser_WhenUserExist() {
        String userName = "John";
        String password = "password";
        int userId = 1;
        when(userDao.existByName(userName)).thenReturn(true);
        User actualUser = userService.createUser(userName, password);
        Assert.assertNull(actualUser);
        verify(userDao).existByName(userName);
        verifyNoMoreInteractions(userDao);
    }
    @Test
    public void testUpdateUser_WhenUserExist() {
        int userId = 1;
        String userNameNew = "John";
        String password = "newPassword";
        User expectedUser = new User(userId, userNameNew, password);
        when(userDao.existById(userId)).thenReturn(true);
        when(userDao.updateUser(userId, userNameNew, password)).thenReturn(true);
        User actualUser = userService.updateUser(userId, userNameNew, password);
        Assert.assertEquals(expectedUser, actualUser);
        verify(userDao).existById(userId);
        verify(userDao).updateUser(userId, userNameNew, password);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testUpdateUser_WhenUserNotExist() {
        int userId = 1;
        String userNameNew = "John";
        String password = "newPassword";
        when(userDao.existById(userId)).thenReturn(false);
        User actualUser = userService.updateUser(userId, userNameNew, password);
        Assert.assertNull(actualUser);
        verify(userDao).existById(userId);
        verifyNoMoreInteractions(userDao);
    }
    @Test
    public void testDeleteUserById_WhenUserExist() {
        int userId = 1;
        when(userDao.existById(userId)).thenReturn(true);
        boolean result = userService.deleteUserById(userId);
        Assert.assertTrue(result);
        verify(userDao).existById(userId);
        verify(userDao).deleteUserById(userId);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testDeleteUserById_WhenUserNotExist() {
        int userId = 1;
        when(userDao.existById(userId)).thenReturn(false);
        boolean result = userService.deleteUserById(userId);
        Assert.assertFalse(result);
        verify(userDao).existById(userId);
        verifyNoMoreInteractions(userDao);
    }
/*    @Test
    public void testSignInVerify_WhenPasswordMatches() {
        String userName = "john";
        String password = "1234567";
        String hashedPassword = "$2a$10$9CDXjneXcK7qPDqS0GJbeO0r.JrJSFMJqjRYiYXNneg/T6PnI3Uqy";
        int userId = 1;
        User user = new User(userId, userName, password);
        when(userDao.findByName(userName)).thenReturn(userId);
        when(userDao.readUserById(userId)).thenReturn(user);
        when(verifyPassword(password, hashedPassword)).thenReturn(true);
        boolean result = userService.signInVerify(userName, password);
        Assert.assertTrue(result);
        verify(userDao).findByName(userName);
        verify(userDao).readUserById(userId);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void testSignInVerify_WhenPasswordNotMatch() {
        String userName = "john";
        String password = "1234567";
        String hashedPassword = "$2a$10$9CDXjneXcK7qPDqS0GJbeO0r.JrJSFMJqjRYiYXNneg/T6PnI3Uqy";
        int userId = 1;
        User user = new User(userId, userName, "wrongPassword");
        when(userDao.findByName(userName)).thenReturn(userId);
        when(userDao.readUserById(userId)).thenReturn(user);
        when(verifyPassword(password, hashedPassword)).thenReturn(false);
        boolean result = userService.signInVerify(userName, password);
        Assert.assertFalse(result);
        verify(userDao).findByName(userName);
        verify(userDao).readUserById(userId);
        verifyNoMoreInteractions(userDao);
    }*/
}

