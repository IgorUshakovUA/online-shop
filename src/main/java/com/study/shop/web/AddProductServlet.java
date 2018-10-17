package com.study.shop.web;

import com.study.shop.entity.Product;
import com.study.shop.service.ProductService;
import com.study.shop.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addProduct.html");

        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        String picturePath = req.getParameter("picturePath");

        int id = productService.add(name, price, picturePath);

        String url = "/products/" + id;

        resp.sendRedirect(url);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
