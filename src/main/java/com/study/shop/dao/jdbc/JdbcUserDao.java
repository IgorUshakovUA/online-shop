package com.study.shop.dao.jdbc;

import com.study.shop.dao.UserDao;
import com.study.shop.dao.jdbc.mapper.UserRowMapper;
import com.study.shop.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUserDao implements UserDao {
    private static final String GET_USER_BY_NAME_AND_PASSWORD_SQL = "SELECT username, password, salt, user_role FROM app_user WHERE username = UPPER(?) AND password = MD5(CONCAT(?,salt))";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private DataSource dataSource;

    public JdbcUserDao() {

    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getUser(String userName, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_NAME_AND_PASSWORD_SQL);) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    User user = USER_ROW_MAPPER.mapRow(resultSet);
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
