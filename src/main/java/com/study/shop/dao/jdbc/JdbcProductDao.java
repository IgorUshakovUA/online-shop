package com.study.shop.dao.jdbc;

import com.study.shop.dao.ProductDao;
import com.study.shop.dao.jdbc.mapper.ProductRowMapper;
import com.study.shop.entity.Product;

import java.io.FileReader;
import java.security.InvalidParameterException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JdbcProductDao implements ProductDao {
    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/application.properties"));
            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, price, add_date, picture_path FROM product");
             ResultSet resultSet = preparedStatement.executeQuery();) {

            List<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getById(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, price, add_date, picture_path FROM product WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Product> productList = new ArrayList<>();

            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                productList.add(product);
            }

            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, String name, double price, LocalDateTime addTime, String picturePath) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET name = ?, price = ?, add_date = ?, picture_path = ? WHERE id = ?")
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(addTime));
            preparedStatement.setString(4, picturePath);
            preparedStatement.setInt(5, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("id = " + id + " not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id = ?")
        ) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("id = " + id + " not found!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int add(String name, double price, String picturePath) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product (id, name, price, picture_path) VALUES(DEFAULT, ?, ?, ?)");
             PreparedStatement serialStatement = connection.prepareStatement("SELECT currval('product_id_seq')")
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, picturePath);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Cannot insert a new row!");
            }

            ResultSet resultSet = serialStatement.executeQuery();
            int id = -1;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                break;
            }
            resultSet.close();

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
