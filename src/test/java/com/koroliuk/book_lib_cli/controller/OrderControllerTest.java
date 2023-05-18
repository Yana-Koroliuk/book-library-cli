package com.koroliuk.book_lib_cli.controller;

import static org.mockito.Mockito.*;

import com.koroliuk.book_lib_cli.SessionManager;
import com.koroliuk.book_lib_cli.model.Order;
import com.koroliuk.book_lib_cli.service.OrderService;
import org.junit.Assert;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderControllerTest {
    private OrderService orderService;

    private OrderController orderController;

    @Before
    public void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    public void testOrderBook_WhenValidInput() {
        String input = "1 [2022-05-01, 2022-05-10]";
        int bookId = 1;
        java.sql.Date startTime = java.sql.Date.valueOf("2022-05-01");
        java.sql.Date endTime = java.sql.Date.valueOf("2022-05-10");
        String currentUser = "testUser";
        int userId = 1;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.orderBook(startTime, endTime, currentUser, bookId)).thenReturn(new Order(1, startTime, endTime, userId, bookId, false));
        orderController.orderBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser, times(2));
        verify(orderService).orderBook(startTime, endTime, currentUser, bookId);
        mockedStatic.close();
    }

    @Test
    public void testOrderBook_WhenOrderIsNull() {
        String input = "1 [2022-05-01, 2022-05-10]";
        int bookId = 1;
        java.sql.Date startTime = java.sql.Date.valueOf("2022-05-01");
        java.sql.Date endTime = java.sql.Date.valueOf("2022-05-10");
        String currentUser = "testUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.orderBook(startTime, endTime, currentUser, bookId)).thenReturn(null);
        orderController.orderBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser, times(2));
        verify(orderService).orderBook(startTime, endTime, currentUser, bookId);
        mockedStatic.close();
    }

    @Test
    public void testOrderBook_WhenUserIsNull() {
        String input = "1 [2022-05-01, 2022-05-10]";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        orderController.orderBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testReturnBook_WhenValidInput() {
        String input = "1";
        int orderId = 1;
        String currentUser = "testUser";
        int userId = 1;
        int bookId = 1;
        java.sql.Date startTime = java.sql.Date.valueOf("2022-05-01");
        java.sql.Date endTime = java.sql.Date.valueOf("2022-05-10");
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.returnBook(orderId)).thenReturn(new Order(orderId, startTime, endTime, userId, bookId, true));
        orderController.returnBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(orderService).returnBook(orderId);
        mockedStatic.close();
    }

    @Test
    public void testReturnBook_WhenOrderIsNull() {
        String input = "1";
        int orderId = 1;
        String currentUser = "testUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.returnBook(orderId)).thenReturn(null);
        orderController.returnBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(orderService).returnBook(orderId);
        mockedStatic.close();
    }

    @Test
    public void testReturnBook_WhenUserIsNull() {
        String input = "1";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        orderController.returnBook(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testDeleteOrder_WhenValidInput() {
        String input = "1";
        int orderId = 1;
        String currentUser = "testUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.deleteOrderById(orderId)).thenReturn(true);
        orderController.deleteOder(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(orderService).deleteOrderById(orderId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteOrder_WhenOrderNotExist() {
        String input = "1";
        int orderId = 1;
        String currentUser = "testUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.deleteOrderById(orderId)).thenReturn(false);
        orderController.deleteOder(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        verify(orderService).deleteOrderById(orderId);
        mockedStatic.close();
    }

    @Test
    public void testDeleteOrder_WhenUserIsNull() {
        String input = "1";
        int orderId = 1;
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        orderController.deleteOder(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testCheckOrdersOfUser_WhenValidInput() {
        String input = "";
        String currentUser = "testUser";
        List<List<String>> books = new ArrayList<>();
        books.add(Arrays.asList("Book 1", "Book 2"));
        books.add(Arrays.asList("Book 3", "Book 4"));
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.checkOrdersOfUser(currentUser)).thenReturn(books);
        orderController.checkOrdersOfUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser, times(2));
        verify(orderService).checkOrdersOfUser(currentUser);
        mockedStatic.close();
    }

    @Test
    public void testCheckOrdersOfUser_WhenBooksEmpty() {
        String input = "";
        String currentUser = "testUser";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(currentUser);
        when(orderService.checkOrdersOfUser(currentUser)).thenReturn(null);
        orderController.checkOrdersOfUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser, times(2));
        verify(orderService).checkOrdersOfUser(currentUser);
        mockedStatic.close();
    }

    @Test
    public void testCheckOrdersOfUser_WhenUserIsNull() {
        String input = "";
        MockedStatic<SessionManager> mockedStatic = Mockito.mockStatic(SessionManager.class);
        mockedStatic.when(SessionManager::getCurrentUser).thenReturn(null);
        orderController.checkOrdersOfUser(input);
        mockedStatic.verify(SessionManager::getCurrentUser);
        mockedStatic.close();
    }

    @Test
    public void testConvertToDate_WhenValidInput() {
        List<String> dates = Arrays.asList("2022-01-01", "2022-02-01");
        List<java.sql.Date> expectedDates = Arrays.asList(java.sql.Date.valueOf("2022-01-01"), java.sql.Date.valueOf("2022-02-01"));
        List<java.sql.Date> result = orderController.convertToDate(dates);
        Assert.assertEquals(expectedDates, result);
    }

}
