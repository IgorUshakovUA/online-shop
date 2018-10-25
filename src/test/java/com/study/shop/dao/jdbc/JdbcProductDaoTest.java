package com.study.shop.dao.jdbc;

import com.study.shop.entity.Product;
import com.study.shop.util.ResourceUtil;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class JdbcProductDaoTest {
    ResourceUtil resourceUtil = new ResourceUtil();

    @Test
    public void testGetAll() throws Exception {
        // Application properties
        Properties properties = new Properties();
        properties.load(resourceUtil.getResourceAsStream("application.properties"));
        String driver = properties.getProperty("jdbc.driver");
        String serverName = properties.getProperty("jdbc.serverName");
        int portNumber = Integer.parseInt(properties.getProperty("jdbc.portNumber"));
        String databaseName = properties.getProperty("jdbc.databaseName");
        String userName = properties.getProperty("jdbc.userName");
        String password = properties.getProperty("jdbc.password");
        Class.forName(driver);


        // Data source
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setPortNumber(portNumber);
        dataSource.setUser(userName);
        dataSource.setPassword(password);

        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSource);
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