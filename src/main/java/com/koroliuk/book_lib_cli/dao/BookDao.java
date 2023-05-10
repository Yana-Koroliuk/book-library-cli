package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;
import com.koroliuk.book_lib_cli.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    Connection connection = DbManager.getInstance().getConnection();
    public int createBook(String bookName, int categoryId) {
        String query = "INSERT INTO Book(title, category_id) VALUES (?, ?);";
        int generatedId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId =  resultSet.getInt(1);
            }
        } catch (SQLException e) {
            return 0;
        }
        return generatedId;
    }
    public Boolean existBook(String bookName) {
        String query = "SELECT * FROM Book WHERE title = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateBook(Book book) {
        String query = "UPDATE Book set title = ?, category_id = ? where ID = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getCategory_id());
            preparedStatement.setInt(3, book.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean deleteBookById(int id) {
        String query = "DELETE FROM book WHERE id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //public Integer findByName(String title)
}
