package com.study.shop.web;

import com.study.shop.entity.Token;
import com.study.shop.service.AuthService;
import com.study.shop.service.ProductService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteProductServlet extends HttpServlet {
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
        } else if (role == null || role.equals("USER")) {
            resp.sendRedirect("/login/You are not authorized to modify products");
        } else {
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
