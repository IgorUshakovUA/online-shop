package com.study.shop.locator;

import com.study.shop.dao.ProductDao;
import com.study.shop.dao.UserDao;
import com.study.shop.dao.jdbc.JdbcProductDao;
import com.study.shop.dao.jdbc.JdbcUserDao;
import com.study.shop.security.SecurityService;
import com.study.shop.service.DefaultProductService;
import com.study.shop.service.DefaultUserService;
import com.study.shop.service.ProductService;
import com.study.shop.util.ResourceUtil;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

public class ServiceLocator {
    private static final HashMap<Class<?>, Object> SERVICES = new HashMap<>();
    private static final HashMap<String, String> PARAMETERS = new HashMap<>();

    static {
        // Application properties
        String serverName;
        int portNumber;
        String databaseName;
        String userName;
        String password;

        Map<String, String> env = System.getenv();
        String portStr = env.get("PORT");

        if (portStr == null) {
            PARAMETERS.put("port", "8080");
            serverName = "localhost";
            portNumber = 5432;
            databaseName = "app_owner";
            userName = "app_owner";
            password = "app_owner";
        } else {
            PARAMETERS.put("port", portStr);
            Properties properties = new Properties();
            try {
                properties.load(ResourceUtil.getResourceAsStream("application.properties"));
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
            serverName = properties.getProperty("jdbc.serverName");
            portNumber = Integer.parseInt(properties.getProperty("jdbc.portNumber"));
            databaseName = properties.getProperty("jdbc.databaseName");
            userName = properties.getProperty("jdbc.userName");
            password = properties.getProperty("jdbc.password");
        }


        // Data source
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(serverName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setPortNumber(portNumber);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        registerService(DataSource.class, dataSource);


        // dao config
        ProductDao productDao = new JdbcProductDao(dataSource);
        registerService(ProductDao.class, productDao);
        UserDao userDao = new JdbcUserDao(dataSource);
        registerService(UserDao.class, userDao);

        // security
        DefaultUserService defaultUserService = new DefaultUserService();
        defaultUserService.setUserDao(userDao);
        registerService(DefaultUserService.class, defaultUserService);
        SecurityService securityService = new SecurityService(defaultUserService);
        registerService(SecurityService.class, securityService);

        // service config
        DefaultProductService defaultProductService = new DefaultProductService();
        defaultProductService.setProductDao(productDao);
        registerService(ProductService.class, defaultProductService);
    }

    public static void registerService(Class<?> serviceClazz, Object service) {
        SERVICES.put(serviceClazz, service);
    }


    public static <T> T getService(Class<T> serviceClass) {
        Object service = SERVICES.get(serviceClass);
        return serviceClass.cast(service);
    }

    public static String getParameterValue(String name) {
        return PARAMETERS.get(name);
    }
}
