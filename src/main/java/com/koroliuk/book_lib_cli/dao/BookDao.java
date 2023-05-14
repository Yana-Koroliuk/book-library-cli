package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;
import com.koroliuk.book_lib_cli.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    Connection connection = DbManager.getInstance().getConnection();
    public int createBook(String bookName, int categoryId, int exemplars) {
        String query = "INSERT INTO Book(title, category_id, exemplars) VALUES (?, ?, ?);";
        int generatedId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setInt(3, exemplars);
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
    public Boolean updateBook(int bookId, String bookName, int categoryId, int exemplars) {
        String query = "UPDATE Book set title = ?, category_id = ?, exemplars = ? where ID = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, bookName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setInt(3, exemplars);
            preparedStatement.setInt(4, bookId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateExemplars(int bookId, int exemplars) {
        String query = "UPDATE Book set  exemplars = ? where ID = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, exemplars);
            preparedStatement.setInt(2, bookId);
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
    public Integer findExemplarsByBookId(int bookId) {
        String query = "SELECT exemplars FROM Book WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("exemplars");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> findBookByAuthor (String authorName) {
        List<String> bookTitles = new ArrayList<>();
        String query = "SELECT B.title FROM Book B " +
                "JOIN Book_Author BA on BA.book_id = B.id " +
                "JOIN Author A on BA.author_id = A.id " +
                "WHERE A.name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, authorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                bookTitles.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookTitles;
    }
    public List<String> findBookByCategory (String categoryName) {
        List<String> bookTitles = new ArrayList<>();
        String query = "SELECT B.title FROM Book B " +
                "JOIN Category C on C.id = B.category_id " +
                "WHERE C.name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                bookTitles.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookTitles;
    }
    public List<String> findBookByPartTitle (String partTitle) {
        List<String> bookTitles = new ArrayList<>();
        String query = "SELECT * FROM Book\n" +
                "WHERE title LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + partTitle + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                bookTitles.add(title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookTitles;
    }
    public List<List<String>> findBookByTitleAuthorCategory (String title, String author, String category) {
        List<List<String>> books = new ArrayList<>();
        String query = "SELECT b.id, b.title, a.name AS author_name, c.name AS category_name FROM book B" +
                "         JOIN book_author BA ON B.id = BA.book_id" +
                "         JOIN author A ON BA.author_id = A.id" +
                "         JOIN category C ON B.category_id = C.id\n" +
                "WHERE B.title = ?" +
                "  AND A.name =  ?" +
                "  AND C.name =  ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String bookId = String.valueOf(resultSet.getInt("id"));
                String bookName = resultSet.getString("title");
                String authorName = resultSet.getString("author_name");
                String categoryName = resultSet.getString("category_name");
                List<String> book = new ArrayList<>();
                book.add(bookId);
                book.add(bookName);
                book.add(authorName);
                book.add(categoryName);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
