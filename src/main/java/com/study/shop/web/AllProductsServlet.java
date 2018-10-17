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

public class AllProductsServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uriParts = req.getRequestURI().split("/");

        List<Product> products;
        if (uriParts.length > 2) {
            try {
                products = productService.getById(Integer.parseInt(uriParts[2]));
            }
            catch(NumberFormatException e) {
                products = new ArrayList<>();
            }

        } else {
            products = productService.getAll();
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("products", products);


        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("products.html", params);

        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(page);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
