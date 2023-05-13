package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;
import com.koroliuk.book_lib_cli.model.Category;
import com.koroliuk.book_lib_cli.model.User;

import java.sql.*;

public class CategoryDao {
    Connection connection = DbManager.getInstance().getConnection();
    public int createCategory(String categoryName) {
        String query = "INSERT INTO Category(name) VALUES (?);";
        int generatedId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
    public Boolean existByName(String categoryName) {
        String query = "SELECT id FROM Category WHERE name = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean existById(int categoryId) {
        String query = "SELECT category FROM Category WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean updateCategory(int categoryId, String categoryName) {
        String query = "UPDATE Category set NAME = ? where ID = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Integer findByName(String categoryName) {
        String query = "SELECT id FROM Category WHERE name = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Boolean deleteCategoryById(int categoryId) {
        String query = "DELETE FROM Category WHERE id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Category readCategoryById(int categoryId) {
        String query = "SELECT * FROM Category WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String categoryName = resultSet.getString("name");
                return new Category(id, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
