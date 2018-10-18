package com.study.shop.web;

import com.study.shop.entity.Token;
import com.study.shop.service.AuthService;
import com.study.shop.web.templater.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private List<Token> acceptedTokens;
    private AuthService authService;

    public static String getRoleByValue(String value, List<Token> acceptedTokens) {
        for (Token acceptedToken : acceptedTokens) {
            if (acceptedToken.getValue().equals(value)) {
                return acceptedToken.getRole();
            }
        }
        return "GUEST";
    }

    private Token getTokenByUserName(String userName) {
        for (Token acceptedToken : acceptedTokens) {
            if (acceptedToken.getUserName().equalsIgnoreCase(userName)) {
                return acceptedToken;
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();

        String[] uriParts = req.getRequestURI().split("/");

        String message = "";

        if (uriParts.length > 2) {
            message = uriParts[2];
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("message", message.replace("%20"," "));
        String page = pageGenerator.getPage("login.html", params);

        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String role = authService.login(name, password);

        if (role.equalsIgnoreCase("GUEST")) {
            resp.sendRedirect("/login/Invalid username or password");
        } else {
            Token token = getTokenByUserName(name);
            if (token != null) {
                String value = token.getValue();
                Cookie cookie = new Cookie("user-token", value);
                resp.addCookie(cookie);
            } else {
                String value = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("user-token", value);
                resp.addCookie(cookie);
                token = new Token(value, name, role);
                acceptedTokens.add(token);
            }
            resp.sendRedirect("/products");
        }
    }

    public void setAcceptedTokens(List<Token> acceptedTokens) {
        this.acceptedTokens = acceptedTokens;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
