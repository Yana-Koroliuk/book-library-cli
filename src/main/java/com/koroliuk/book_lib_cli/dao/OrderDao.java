package com.koroliuk.book_lib_cli.dao;

import com.koroliuk.book_lib_cli.DbManager;
import com.koroliuk.book_lib_cli.model.Order;

import java.sql.*;

public class OrderDao {
    Connection connection = DbManager.getInstance().getConnection();
    public int createOrder(Date startTime, Date endTime, int userId) {
        String query = "INSERT INTO \"Order\" (start_time, end_time, user_id, is_returned) VALUES (?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, startTime);
            preparedStatement.setDate(2, endTime);
            preparedStatement.setInt(3, userId);
            preparedStatement.setBoolean(4, false);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean updateOrder(int orderId, Date startTime, Date endTime, int userId, boolean isReturned) {
        String query = "UPDATE \"Order\" SET start_time = ?, end_time = ?, user_id = ?, is_returned = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, startTime);
            preparedStatement.setDate(2, endTime);
            preparedStatement.setInt(3, userId);
            preparedStatement.setBoolean(4, isReturned);
            preparedStatement.setInt(5, orderId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrderById(int orderId) {
        String query = "DELETE FROM \"Order\" WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean existByStartTimeEndTime(Date startTime, Date endTime) {
        String query = "SELECT * FROM \"Order\" WHERE start_time = ? and end_time = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, startTime);
            preparedStatement.setDate(2, endTime);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Boolean existOrderById(int orderId) {
        String query = "SELECT * FROM \"Order\" WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Order readOrderById(int orderId) {
        String query = "SELECT * FROM \"Order\" WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date startTime = resultSet.getDate("start_time");
                Date endTime = resultSet.getDate("end_time");
                int userId = resultSet.getInt("user_id");
                boolean isReturned = resultSet.getBoolean("is_returned");
                return new Order(id, startTime, endTime, userId, isReturned);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
