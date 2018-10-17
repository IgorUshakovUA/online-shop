package com.study.shop.web;

import com.study.shop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriParts = req.getRequestURI().split("/");

        if (uriParts.length > 3) {
            try {
                int id = Integer.parseInt(uriParts[3]);
                productService.delete(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        resp.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
