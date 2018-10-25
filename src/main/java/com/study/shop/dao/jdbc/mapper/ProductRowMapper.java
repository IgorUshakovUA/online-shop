package com.study.shop.dao.jdbc.mapper;

import com.study.shop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();

        String name = resultSet.getString("name");
        int id = resultSet.getInt("id");
        double price = resultSet.getDouble("price");
        Timestamp timestamp = resultSet.getTimestamp("add_date");
        LocalDateTime addDate = timestamp.toLocalDateTime();
        String picturePath = resultSet.getString("picture_path");

        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setAddDate(addDate);
        product.setPicturePath(picturePath);

        return product;
    }
}
