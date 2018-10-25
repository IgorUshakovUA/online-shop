package com.study.shop.web.servlet;

import com.study.shop.locator.ServiceLocator;
import com.study.shop.security.entity.Session;
import com.study.shop.service.ProductService;
import com.study.shop.service.UserService;
import com.study.shop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddProductServlet extends HttpServlet {
    private ProductService productService;

    public AddProductServlet() {
        this.productService = ServiceLocator.getService(ProductService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addProduct.html");

        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        String picturePath = req.getParameter("picturePath");

        int id = productService.add(name, price, picturePath);

        String url = "/products/" + id;

        resp.sendRedirect(url);
    }
}
