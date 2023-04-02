package com.koroliuk.book_lib_cli;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Connection connection = dbManager.getConnection();
        String sql = "INSERT INTO Author(id, name) VALUES (1, 'Aurelius')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        String sql1 = "UPDATE Author set NAME = 'Aram' where ID = 1";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.executeUpdate();
        preparedStatement.close();
        preparedStatement1.close();
    }
}