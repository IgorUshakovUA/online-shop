package com.study.shop;

import com.study.shop.dao.ProductDao;
import com.study.shop.dao.jdbc.JdbcProductDao;
import com.study.shop.service.DefaultProductService;
import com.study.shop.web.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {

    public static void main(String[] args) throws Exception {
        // dao config
        ProductDao productDao = new JdbcProductDao();

        // service config
        DefaultProductService defaultProductService = new DefaultProductService();
        defaultProductService.setProductDao(productDao);

        // servlet config
        AllProductsServlet allProductsServlet = new AllProductsServlet();
        allProductsServlet.setProductService(defaultProductService);
        AddProductServlet addProductServlet = new AddProductServlet();
        addProductServlet.setProductService(defaultProductService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet();
        deleteProductServlet.setProductService(defaultProductService);
        EditProductServlet editProductServlet = new EditProductServlet();
        editProductServlet.setProductService(defaultProductService);

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

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}
