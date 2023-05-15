package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.AuthorDao;
import com.koroliuk.book_lib_cli.dao.UserDao;
import com.koroliuk.book_lib_cli.model.User;

import static com.koroliuk.book_lib_cli.PasswordHasher.verifyPassword;

public class UserService {
    private static UserDao userDao;

    public UserService(UserDao userDao) {
        UserService.userDao = userDao;
    }

    public int createUser(String userName, String password) {
        int userId = 0;
        if (!userDao.existByName(userName)) {
            userId = userDao.createUser(userName, password);
        }
        return userId;
    }

    public User updateUser(int userId, String userNameNew, String password) {
        User user = null;
        if (userDao.existById(userId)) {
            if (userDao.updateUser(userId, userNameNew, password)) {
                user = new User(userId, userNameNew, password);
            }
        }
        return user;
    }

    public boolean deleteUserById(int userId) {
        if (userDao.existById(userId)) {
            userDao.deleteUserById(userId);
            return true;
        }
        return false;
    }

    public Boolean signInVerify(String userName, String password) {
        int userId = userDao.findByName(userName);
        User user = userDao.readUserById(userId);
        if (verifyPassword(password, user.getPassword())) {
            return true;
        }
        return false;
    }
}
