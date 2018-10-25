package com.study.shop.web.servlet;

import com.study.shop.locator.ServiceLocator;
import com.study.shop.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;

    public DeleteProductServlet() {
        this.productService = ServiceLocator.getService(ProductService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] uriParts = req.getRequestURI().split("/");

        try {
            int id = Integer.parseInt(uriParts[3]);
            productService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/products");

    }
}
