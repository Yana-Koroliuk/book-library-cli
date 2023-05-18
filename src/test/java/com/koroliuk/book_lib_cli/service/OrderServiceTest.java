package com.koroliuk.book_lib_cli.service;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.dao.*;
import com.koroliuk.book_lib_cli.model.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceTest {
    private OrderDao orderDao;
    private BookDao bookDao;
    private UserDao userDao;
    private OrderService orderService;

    @Before
    public void setUp() {
        orderDao = mock(OrderDao.class);
        bookDao = mock(BookDao.class);
        userDao = mock(UserDao.class);
        orderService = new OrderService(orderDao, bookDao, userDao);
    }

    @Test
    public void testUpdateOrder_WhenOrderExist() {
        int orderId = 1;
        Date startTimeNew = new Date(2023, 5, 17);
        Date endTimeNew = new Date(2023, 5, 20);
        int userId = 1;
        int bookId = 1;
        boolean isReturned = true;
        Order expectedOrder = new Order(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned);
        when(orderDao.existOrderById(orderId)).thenReturn(true);
        when(orderDao.updateOrder(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned)).thenReturn(true);
        Order actualOrder = orderService.updateOrder(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned);
        Assert.assertEquals(expectedOrder, actualOrder);
        verify(orderDao).existOrderById(orderId);
        verify(orderDao).updateOrder(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testUpdateOrder_WhenOrderNotExist() {
        int orderId = 1;
        Date startTimeNew = new Date(2023, 5, 17);
        Date endTimeNew = new Date(2023, 5, 20);
        int userId = 1;
        int bookId = 1;
        boolean isReturned = true;
        when(orderDao.existOrderById(orderId)).thenReturn(false);
        Order actualOrder = orderService.updateOrder(orderId, startTimeNew, endTimeNew, userId, bookId, isReturned);
        Assert.assertNull(actualOrder);
        verify(orderDao).existOrderById(orderId);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testDeleteOrderById_WhenOrderExist() {
        int orderId = 1;
        when(orderDao.existOrderById(orderId)).thenReturn(true);
        when(orderDao.deleteOrderById(orderId)).thenReturn(true);
        boolean actualResult = orderService.deleteOrderById(orderId);
        Assert.assertTrue(actualResult);
        verify(orderDao).existOrderById(orderId);
        verify(orderDao).deleteOrderById(orderId);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testDeleteOrderById_WhenOrderNotExist() {
        int orderId = 1;
        when(orderDao.existOrderById(orderId)).thenReturn(false);
        boolean actualResult = orderService.deleteOrderById(orderId);
        Assert.assertFalse(actualResult);
        verify(orderDao).existOrderById(orderId);
        verifyNoMoreInteractions(orderDao);
    }

    @Test
    public void testOrderBook_WhenOrderExist() {
        Date startTime = new Date(2023, 5, 17);
        Date endTime = new Date(2023, 5, 20);
        String userName = "John";
        int userId = 1;
        int bookId = 1;
        int orderId = 1;
        int exemplars = 2;
        Order expectedOrder = new Order(orderId, startTime, endTime, userId, bookId, false);
        when(userDao.findByName(userName)).thenReturn(userId);
        when(bookDao.existBookById(bookId)).thenReturn(true);
        when(bookDao.findExemplarsByBookId(bookId)).thenReturn(exemplars);
        when(orderDao.createOrder(startTime, endTime, userId, bookId)).thenReturn(orderId);
        Order actualOrder = orderService.orderBook(startTime, endTime, userName, bookId);
        Assert.assertEquals(expectedOrder, actualOrder);
        verify(userDao).findByName(userName);
        verify(bookDao).existBookById(bookId);
        verify(bookDao).findExemplarsByBookId(bookId);
        verify(orderDao).createOrder(startTime, endTime, userId, bookId);
        verify(bookDao).updateExemplars(bookId, exemplars - 1);
        verifyNoMoreInteractions(bookDao, userDao, orderDao);
    }

    @Test
    public void testOrderBook_WhenOrderNotExist() {
        Date startTime = new Date(2023, 5, 17);
        Date endTime = new Date(2023, 5, 20);
        String userName = "John";
        int userId = 1;
        int bookId = 1;
        int orderId = 1;
        int exemplars = 2;
        when(userDao.findByName(userName)).thenReturn(userId);
        when(bookDao.existBookById(bookId)).thenReturn(false);
        Order actualOrder = orderService.orderBook(startTime, endTime, userName, bookId);
        Assert.assertNull(actualOrder);
        verify(userDao).findByName(userName);
        verify(bookDao).existBookById(bookId);
        verifyNoMoreInteractions(bookDao, userDao, orderDao);
    }

    @Test
    public void testOrderBook_WhenNoExemplarsAvailable() {
        Date startTime = new Date(2023, 5, 17);
        Date endTime = new Date(2023, 5, 20);
        String userName = "John";
        int bookId = 1;
        int userId = 1;
        int exemplars = 0;
        when(userDao.findByName(userName)).thenReturn(userId);
        when(bookDao.existBookById(bookId)).thenReturn(true);
        when(bookDao.findExemplarsByBookId(bookId)).thenReturn(exemplars);
        Order actualOrder = orderService.orderBook(startTime, endTime, userName, bookId);
        Assert.assertNull(actualOrder);
        verify(bookDao).existBookById(bookId);
        verify(bookDao).findExemplarsByBookId(bookId);
        verify(userDao).findByName(userName);
        verifyNoMoreInteractions(bookDao, userDao, orderDao);
    }

    @Test
    public void testReturnBook_WhenOrderExist() {
        Date startTime = new Date(2023, 5, 17);
        Date endTime = new Date(2023, 5, 20);
        int orderId = 1;
        int bookId = 1;
        int userId = 1;
        int exemplars = 2;
        Order expectedOrder = new Order(orderId, startTime, endTime, userId, bookId, true);
        when(orderDao.existOrderById(orderId)).thenReturn(true);
        when(orderDao.readOrderById(orderId)).thenReturn(new Order(orderId, startTime, endTime, userId, bookId, false));
        when(bookDao.findExemplarsByBookId(bookId)).thenReturn(exemplars);
        when(orderDao.updateOrder(orderId, startTime, endTime, userId, bookId, true)).thenReturn(true);
        Order actualOrder = orderService.returnBook(orderId);
        Assert.assertEquals(expectedOrder, actualOrder);
        verify(orderDao, times(2)).existOrderById(orderId);
        verify(orderDao).readOrderById(orderId);
        verify(bookDao).findExemplarsByBookId(bookId);
        verify(bookDao).updateExemplars(bookId, exemplars + 1);
        verify(orderDao).updateOrder(orderId, startTime, endTime, userId, bookId, true);
        verifyNoMoreInteractions(orderDao, bookDao);
    }

    @Test
    public void testReturnBook_WhenOrderNotExist() {
        int orderId = 1;
        when(orderDao.existOrderById(orderId)).thenReturn(false);
        Order returnedOrder = orderService.returnBook(orderId);
        Assert.assertNull(returnedOrder);
        verify(orderDao).existOrderById(orderId);
        verifyNoMoreInteractions(orderDao, bookDao);
    }

    @Test
    public void testReturnBook_WhenOrderAlreadyReturned() {
        int orderId = 1;
        int bookId = 1;
        int userId = 1;
        Date startTime = new Date(2023, 5, 17);
        Date endTime = new Date(2023, 5, 20);
        Order order = new Order(orderId, startTime, endTime, userId, bookId, true);
        when(orderDao.existOrderById(orderId)).thenReturn(true);
        when(orderDao.readOrderById(orderId)).thenReturn(order);
        Order returnedOrder = orderService.returnBook(orderId);
        Assert.assertNull(returnedOrder);
        verify(orderDao).existOrderById(orderId);
        verify(orderDao).readOrderById(orderId);
        verifyNoMoreInteractions(orderDao, bookDao);
    }

    @Test
    public void testCheckOrdersOfUser() {
        Date startTime = new Date(2023, 5, 17);
        Date endTime = new Date(2023, 5, 20);
        String userName = "John";
        int userId = 1;
        int bookId1 = 1;
        int bookId2 = 2;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, startTime, endTime, userId, bookId1, true));
        orders.add(new Order(2, startTime, endTime, userId, bookId2, false));
        List<String> bookReturned = new ArrayList<>();
        List<String> bookNotReturned = new ArrayList<>();
        bookReturned.add("Book 1");
        bookNotReturned.add("Book 2");
        when(userDao.findByName(userName)).thenReturn(userId);
        when(orderDao.checkOrdersOfUser(userId)).thenReturn(orders);
        when(bookDao.findById(bookId1)).thenReturn("Book 1");
        when(bookDao.findById(bookId2)).thenReturn("Book 2");
        List<List<String>> expectedBooks = new ArrayList<>();
        expectedBooks.add(bookReturned);
        expectedBooks.add(bookNotReturned);
        List<List<String>> actualBooks = orderService.checkOrdersOfUser(userName);
        Assert.assertEquals(expectedBooks, actualBooks);
        verify(userDao).findByName(userName);
        verify(orderDao).checkOrdersOfUser(userId);
        verify(bookDao).findById(bookId1);
        verify(bookDao).findById(bookId2);
        verifyNoMoreInteractions(userDao, orderDao, bookDao);
    }
}
