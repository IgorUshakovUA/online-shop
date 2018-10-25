package com.study.shop.web.servlet;

import com.study.shop.locator.ServiceLocator;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.Session;
import com.study.shop.util.CookieUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddToCartServlet extends HttpServlet {
    private CookieUtil cookieUtil = new CookieUtil();
    private SecurityService securityService;

    public AddToCartServlet() {
        this.securityService = ServiceLocator.getService(SecurityService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userToken = cookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);

        String[] uriParts = req.getRequestURI().split("/");

        try {
            int id = Integer.parseInt(uriParts[2]);
            session.getCart().addProduct(id,1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/products");

    }
}
