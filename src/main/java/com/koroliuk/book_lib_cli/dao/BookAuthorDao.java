package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;

import java.sql.*;

public class BookAuthorDao {
    Connection connection = DbManager.getInstance().getConnection();
    public Boolean creatBookAuthor(int bookId, int authorId) {
        String query = "INSERT INTO Book_Author (book_id, author_id) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean existBookAuthor(int bookId, int authorId) {
        String query = "SELECT * FROM Book_Author WHERE book_id = ? and author_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
