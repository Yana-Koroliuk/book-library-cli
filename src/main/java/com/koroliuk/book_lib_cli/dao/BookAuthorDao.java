package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookAuthorDao {
    Connection connection = DbManager.getInstance().getConnection();

    public Boolean createBookAuthor(int bookId, int authorId) {
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

    public List<Integer> findByBookId(int bookId) {
        List<Integer> authorsId = new ArrayList<>();
        String query = "SELECT author_id FROM Book_Author WHERE book_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                authorsId.add(resultSet.getInt("author_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorsId;
    }

    public Boolean deleteBookAuthorByBookAuthorId(int bookId, int authorId) {
        String query = "DELETE FROM Book_Author WHERE book_id = ? and author_id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, authorId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAuthorUsedInOtherBooks(int authorId, int bookId) {
        String query = "SELECT COUNT(*) FROM Book_Author WHERE author_id = ? AND book_id != ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
