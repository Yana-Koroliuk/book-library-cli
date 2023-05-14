package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.UserDao;
import com.koroliuk.book_lib_cli.model.Book;
import com.koroliuk.book_lib_cli.model.Order;
import com.koroliuk.book_lib_cli.model.User;

import java.sql.Date;

import static com.koroliuk.book_lib_cli.PasswordHasher.verifyPassword;

public class UserService {
    UserDao userDao = new UserDao();

    public int createUser(String userName, String password) {
        int userId = 0;
        if (!userDao.existByName(userName)) {
            userId =  userDao.createUser(userName, password);
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
    public User deleteUserById(int userId) {
        User user = null;
        if (userDao.existById(userId)) {
            user = userDao.readUserById(userId);
            userDao.deleteUserById(userId);
        }
        return user;
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
