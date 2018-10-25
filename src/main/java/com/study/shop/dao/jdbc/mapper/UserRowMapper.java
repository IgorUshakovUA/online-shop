package com.study.shop.dao.jdbc.mapper;

import com.study.shop.entity.User;
import com.study.shop.security.entity.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) {
        try {
            String name = resultSet.getString("username");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            UserRole userRole = UserRole.valueOf(resultSet.getString("user_role"));
            return new User(name, password, salt, userRole);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
