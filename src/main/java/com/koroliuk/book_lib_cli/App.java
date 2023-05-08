package com.koroliuk.book_lib_cli;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        QueryManager queryManager = new QueryManager();
        Controller controller = new Controller(queryManager);
        controller.run();
    }
}