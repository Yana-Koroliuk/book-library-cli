package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.UserDao;
import com.koroliuk.book_lib_cli.model.Order;
import com.koroliuk.book_lib_cli.model.User;

import java.sql.Date;

public class UserService {
    UserDao userDao = new UserDao();

    public int createUser(String userName) {
        int userId = 0;
        if (!userDao.existByName(userName)) {
            userId =  userDao.createUser(userName);
        }
        return userId;
    }
    public User updateUser(int userId, String userNameNew) {
        User user = null;
        if (userDao.existById(userId)) {
            if (userDao.updateUser(userId, userNameNew)) {
                user = new User(userId, userNameNew);
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
}
