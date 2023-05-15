package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.*;
import com.koroliuk.book_lib_cli.model.Order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static OrderDao orderDao;
    private static BookDao bookDao;
    private static UserDao userDao;

    public OrderService(OrderDao orderDao, BookDao bookDao, UserDao userDao) {
        OrderService.orderDao = orderDao;
        OrderService.bookDao = bookDao;
        OrderService.userDao = userDao;
    }

    public int createOrder(Date startTime, Date endTime, int userId, int bookId) {
        return orderDao.createOrder(startTime, endTime, userId, bookId);
    }

    public Order updateOrder(int orderId, Date startTimeNew, Date endTimeNew, int userId, int bookId, boolean isReturned) {
        Order order = null;
        if (orderDao.existOrderById(orderId)) {
            if (orderDao.updateOrder(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned)) {
                order = new Order(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned);
            }
        }
        return order;
    }

    public boolean deleteOrderById(int orderId) {
        if (orderDao.existOrderById(orderId)) {
            orderDao.deleteOrderById(orderId);
            return true;
        }
        return false;
    }

    public Order orderBook(Date startTime, Date endTime, String userName, int bookId) {
        Order order = null;
        int exemplars = 0;
        int userId = userDao.findByName(userName);
        if (bookDao.existBookById(bookId)) {
            exemplars = bookDao.findExemplarsByBookId(bookId);
            if (exemplars > 0) {
                int orderId = orderDao.createOrder(startTime, endTime, userId, bookId);
                bookDao.updateExemplars(bookId, exemplars - 1);
                order = new Order(orderId, startTime, endTime, userId, bookId, false);
            }
        }
        return order;
    }

    public Order returnBook(int orderId) {
        Order order = null;
        int exemplars = 0;
        if (orderDao.existOrderById(orderId)) {
            order = orderDao.readOrderById(orderId);
            if (!order.getReturned()) {
                exemplars = bookDao.findExemplarsByBookId(order.getBookId());
                bookDao.updateExemplars(order.getBookId(), exemplars + 1);
                order = updateOrder(orderId, order.getStartTime(), order.getEndTime(), order.getUserId(), order.getBookId(), true);
            } else {
                order = null;
            }
        }
        return order;
    }

    public List<List<String>> checkOrdersOfUser(String userName) {
        List<List<String>> books = new ArrayList<>();
        int userId = userDao.findByName(userName);
        List<Order> orders = orderDao.checkOrdersOfUser(userId);
        List<String> bookReturned = new ArrayList<>();
        List<String> bookNotReturned = new ArrayList<>();
        for (Order order : orders) {
            String bookName = bookDao.findById(order.getBookId());
            if (order.getReturned()) {
                bookReturned.add(bookName);
            } else {
                bookNotReturned.add(bookName);
            }
        }
        books.add(bookReturned);
        books.add(bookNotReturned);
        return books;
    }
}
