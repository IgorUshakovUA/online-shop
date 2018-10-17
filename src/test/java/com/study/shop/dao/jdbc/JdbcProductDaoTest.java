package com.study.shop.dao.jdbc;

import com.study.shop.entity.Product;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcProductDaoTest {

    @Test
    public void testGetAll() {
        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        List<Product> products = jdbcProductDao.getAll();

        assertFalse(products.isEmpty());

        for (Product product : products) {
            assertNotEquals(0, product.getId());
            assertNotNull(product.getName());
            assertNotNull(product.getAddDate());
            assertNotNull(product.getPrice());
        }
    }

}