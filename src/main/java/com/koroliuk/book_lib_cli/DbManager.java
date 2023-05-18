package com.koroliuk.book_lib_cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbManager {
    private static DbManager instance;
    private Connection connection = null;

    private DbManager() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/librarydb";
            String username = "userk";
            String password = "1234";
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DbManager getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DbManager();
            }
            return instance;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance;
    }
}