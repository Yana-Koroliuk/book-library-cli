package com.koroliuk.book_lib_cli.controller;

import com.koroliuk.book_lib_cli.model.Order;
import com.koroliuk.book_lib_cli.service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.koroliuk.book_lib_cli.SessionManager.getCurrentUser;

public class OrderController {
    private static OrderService orderService;

    public OrderController(OrderService orderService) {
        OrderController.orderService = orderService;
    }

    public void orderBook(String input) {
        if (getCurrentUser() != null) {
            int bookId = Integer.parseInt(input.substring(0, input.indexOf(" ")));
            String datesString = input.substring(input.indexOf("[") + 1, input.indexOf("]"));
            List<String> dates = Arrays.asList(datesString.split(", "));
            List<java.sql.Date> times = convertToDate(dates);
            java.sql.Date startTime = times.get(0);
            java.sql.Date endTime = times.get(1);
            Order order = orderService.orderBook(startTime, endTime, getCurrentUser(), bookId);
            if (order != null) {
                System.out.println("id | start time | end time | user id | book id | is returned");
                System.out.println("------------------------------------------------------------");
                System.out.println(order.getId() + " | " + order.getStartTime() + " | " + order.getEndTime() + " | " + order.getUserId() + " | " + order.getBookId() + " | " + order.getReturned());
            } else {
                System.out.println("Not available exemplars");
            }
        } else {
            System.out.println("You need to login");
        }
    }

    public void returnBook(String input) {
        if (getCurrentUser() != null) {
            int orderId = Integer.parseInt(input.trim());
            Order order = orderService.returnBook(orderId);
            if (order != null) {
                System.out.println("id | start time | end time | user id | book id | is returned");
                System.out.println("------------------------------------------------------------");
                System.out.println(order.getId() + " | " + order.getStartTime() + " | " + order.getEndTime() + " | " + order.getUserId() + " | " + order.getBookId() + " | " + order.getReturned());
            } else {
                System.out.println("Not exist");
            }
        } else {
            System.out.println("You need to login");
        }
    }

    public void deleteOder(String input) {
        int orderId = Integer.parseInt(input.trim());
        String currentUser = getCurrentUser();
        if (currentUser != null) {
            if (orderService.deleteOrderById(orderId)) {
                System.out.println("Successful delete");
            } else {
                System.out.println("Not exist");
            }
        } else {
            System.out.println("No possibility");
        }
    }

    public void checkOrdersOfUser(String input) {
        if (getCurrentUser() != null) {
            List<List<String>> books = orderService.checkOrdersOfUser(getCurrentUser());
            if (books != null) {
                System.out.println("Returned books:");
                if (books.get(0).size() == 0) {
                    System.out.println("Not exist");
                } else {
                    for (int i = 0; i < books.get(0).size(); i++) {
                        System.out.println(books.get(0).get(i));
                    }
                }
                System.out.println("Unreturned books:");
                if (books.get(1).size() == 0) {
                    System.out.println("Not exist");
                } else {
                    for (int i = 0; i < books.get(1).size(); i++) {
                        System.out.println(books.get(1).get(i));
                    }
                }
            } else {
                System.out.println("Has no orders");
            }
        } else {
            System.out.println("You need to login");
        }
    }

    public List<java.sql.Date> convertToDate(List<String> dates) {
        List<java.sql.Date> times = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (String date : dates) {
                java.util.Date startDate = dateFormat.parse(date);
                java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                times.add(sqlStartDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }
}
