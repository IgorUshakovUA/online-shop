package com.study.shop;

import com.study.shop.dao.AuthDao;
import com.study.shop.dao.ProductDao;
import com.study.shop.dao.jdbc.JdbcAuthDao;
import com.study.shop.dao.jdbc.JdbcProductDao;
import com.study.shop.entity.Token;
import com.study.shop.service.DefaultAuthService;
import com.study.shop.service.DefaultProductService;
import com.study.shop.web.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.List;

public class Starter {

    public static void main(String[] args) throws Exception {
        // dao config
        ProductDao productDao = new JdbcProductDao();
        AuthDao authDao = new JdbcAuthDao();

        // security
        List<Token> acceptedTokens = new ArrayList<>();

        // service config
        DefaultProductService defaultProductService = new DefaultProductService();
        defaultProductService.setProductDao(productDao);
        DefaultAuthService defaultAuthService = new DefaultAuthService();
        defaultAuthService.setAuthDao(authDao);

        // servlet config
        AllProductsServlet allProductsServlet = new AllProductsServlet();
        allProductsServlet.setProductService(defaultProductService);
        allProductsServlet.setAuthService(defaultAuthService);
        allProductsServlet.setAcceptedTokens(acceptedTokens);
        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(defaultProductService);
        addProductServlet.setAuthService(defaultAuthService);
        addProductServlet.setAcceptedTokens(acceptedTokens);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet();
        deleteProductServlet.setProductService(defaultProductService);
        deleteProductServlet.setAuthService(defaultAuthService);
        deleteProductServlet.setAcceptedTokens(acceptedTokens);
        EditProductServlet editProductServlet = new EditProductServlet();
        editProductServlet.setProductService(defaultProductService);
        editProductServlet.setAuthService(defaultAuthService);
        editProductServlet.setAcceptedTokens(acceptedTokens);
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setAcceptedTokens(acceptedTokens);
        loginServlet.setAuthService(defaultAuthService);

        // server config
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setResourceBase("jar:file:!/");
        context.addServlet(new ServletHolder(allProductsServlet), "/");
        context.addServlet(new ServletHolder(allProductsServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/product/add");
        context.addServlet(new ServletHolder(deleteProductServlet), "/product/delete/*");
        context.addServlet(new ServletHolder(editProductServlet), "/product/edit/*");
        context.addServlet(new ServletHolder(allProductsServlet), "/products/*");
        context.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");
        context.addServlet(new ServletHolder(loginServlet), "/login");
        context.addServlet(new ServletHolder(loginServlet), "/login/*");


        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
