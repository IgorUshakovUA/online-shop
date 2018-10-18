package com.study.shop.dao.jdbc;

import com.study.shop.dao.AuthDao;

import java.sql.*;

public class JdbcAuthDao implements AuthDao {

    @Override
    public String login(String userName, String password) {
        String role = "GUEST";

        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_role FROM app_user WHERE username = UPPER(?) AND password = UPPER(?)");
             ) {

            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                role = resultSet.getString("user_role");
                break;
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return role;
    }
}
