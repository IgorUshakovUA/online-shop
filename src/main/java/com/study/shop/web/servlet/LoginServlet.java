package com.study.shop.web.servlet;

import com.study.shop.locator.ServiceLocator;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.Session;
import com.study.shop.util.CookieUtil;
import com.study.shop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    private SecurityService securityService;
    private CookieUtil cookieUtil = new CookieUtil();

    public LoginServlet(){
        this.securityService = ServiceLocator.getService(SecurityService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();

        HashMap<String, Object> params = new HashMap<>();
        params.put("message", "");
        String page = pageGenerator.getPage("login.html", params);

        resp.getWriter().write(page); // page > buffer on outputStream
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Session session = securityService.auth(name, password);

        if (session != null) {
            cookieUtil.setCookie("user-session", session.getToken(),resp);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login");
        }
    }

}
