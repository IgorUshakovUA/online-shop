package com.study.shop.dao.jdbc.mapper;

import com.study.shop.entity.Product;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductRowMapperTest {

    @Test
    public void testRowMap() throws SQLException {
        ProductRowMapper productRowMapper = new ProductRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        Timestamp actualAddDate = Timestamp.valueOf(LocalDateTime.now());

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Product name");
        when(resultSet.getDouble("price")).thenReturn(99.99);
        when(resultSet.getTimestamp("add_date")).thenReturn(actualAddDate);
        when(resultSet.getString("picture_path")).thenReturn("image.jpeg");

        Product actualProduct = productRowMapper.mapRow(resultSet);


        assertEquals(1, actualProduct.getId());
        assertEquals("Product name", actualProduct.getName());
        assertEquals(99.99, actualProduct.getPrice(),0.1);
        assertEquals(actualAddDate.toLocalDateTime(),actualProduct.getAddDate());
        assertEquals("image.jpeg",actualProduct.getPicturePath());
    }

}