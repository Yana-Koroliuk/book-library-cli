package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;
import com.koroliuk.book_lib_cli.model.Book;

import java.sql.*;

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
    public Boolean existBookById(int bookId) {
        String query = "SELECT * FROM Book WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public Boolean updateBook(int bookId, String bookName, int categoryId) {
        String query = "UPDATE Book set title = ?, category_id = ? where ID = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setInt(3, bookId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean deleteBookById(int bookId) {
        String query = "DELETE FROM book WHERE id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Integer findByName(String title) {
        String query = "SELECT id FROM Book WHERE title = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String findById(int bookId) {
        String query = "SELECT title FROM Book WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("title");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Integer findCategoryId(int bookId) {
        String query = "SELECT category_id FROM Book WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("category_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
