package com.koroliuk.book_lib_cli.service;

import com.koroliuk.book_lib_cli.dao.OrderDao;
import com.koroliuk.book_lib_cli.model.Order;

import java.sql.Date;

public class OrderService {
    OrderDao orderDao = new OrderDao();

    public int createOrder(Date startTime, Date endTime, int userId) {
        return orderDao.createOrder(startTime, endTime, userId);
    }
    public Order updateOrder(int orderId, Date startTimeNew, Date endTimeNew, int userId, boolean isReturned) {
        Order order = null;
        if (orderDao.existOrderById(orderId)) {
            if (orderDao.updateOrder(orderId, startTimeNew, endTimeNew, userId, isReturned)) {
                order = new Order(orderId, startTimeNew, endTimeNew, userId, isReturned);
            }
        }
        return order;
    }
    public Order deleteOrderById(int orderId) {
        Order order = null;
        if (orderDao.existOrderById(orderId)) {
            order = orderDao.readOrderById(orderId);
            orderDao.deleteOrderById(orderId);
        }
        return order;
    }
}
