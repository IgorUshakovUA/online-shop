package com.study.shop.web;

import com.study.shop.entity.Product;
import com.study.shop.entity.Token;
import com.study.shop.service.AuthService;
import com.study.shop.service.ProductService;
import com.study.shop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllProductsServlet extends HttpServlet {
    private ProductService productService;
    private AuthService authService;
    private List<Token> acceptedTokens;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String role = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    role = LoginServlet.getRoleByValue(cookie.getValue(), acceptedTokens);
                    break;
                }
            }
        }
        if (role == null || role.equals("GUEST")) {
            resp.sendRedirect("/login/You are not authorized yet");
        } else {

            String[] uriParts = req.getRequestURI().split("/");

            List<Product> products;
            if (uriParts.length > 2) {
                try {
                    products = productService.getById(Integer.parseInt(uriParts[2]));
                } catch (NumberFormatException e) {
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
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setAcceptedTokens(List<Token> acceptedTokens) {
        this.acceptedTokens = acceptedTokens;
    }
}
