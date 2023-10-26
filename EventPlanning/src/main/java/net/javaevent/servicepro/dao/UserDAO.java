package net.javaevent.servicepro.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import net.javaevent.servicepro.model.User;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schema?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "1234";

    private static final String INSERT_USERS_SQL = "INSERT INTO users (name, businesstype, email, phoneno, about) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT id, name, businesstype, email, phoneno, about FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USERS_SQL = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_USERS_SQL = "UPDATE users SET name = ?, businesstype = ?, email = ?, phoneno = ?, about = ? WHERE id = ?";

    
//    create database connection
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            // Ensure auto-commit is enabled (each statement is a transaction).
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
// insert details
    public void insertUser(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getBusinesstype());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneno());
            preparedStatement.setString(5, user.getAbout());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// Update user details
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getBusinesstype());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneno());
            statement.setString(5, user.getAbout());
            statement.setInt(6, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
//select user by id
    public User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String businesstype = rs.getString("businesstype");
                String email = rs.getString("email");
                String phoneno = rs.getString("phoneno");
                String about = rs.getString("about");
                user = new User(id, name, businesstype, email, phoneno, about);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
//get all user details
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String businesstype = rs.getString("businesstype");
                String email = rs.getString("email");
                String phoneno = rs.getString("phoneno");
                String about = rs.getString("about");
                users.add(new User(id, name, businesstype, email, phoneno, about));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
//delete user from database
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
//    get count from database
    public int getTotalUserCount() {
        int totalCount = 0;
        String SQL = "SELECT COUNT(*) FROM users";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

//    search by service type(get data from database)
    public List<User> searchUsersByServiceType(String serviceType) {
        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM users WHERE businesstype LIKE ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, "%" + serviceType + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String businesstype = resultSet.getString("businesstype");
                String email = resultSet.getString("email");
                String phoneno = resultSet.getString("phoneno");
                String about = resultSet.getString("about");
                
                // Create a User object and add it to the list
                User user = new User(id, name, businesstype, email, phoneno, about);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    
    
    }


