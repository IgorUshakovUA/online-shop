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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class EditProductServlet extends HttpServlet {
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
                    List<Product> products = productService.getById(Integer.parseInt(uriParts[3]));
                    HashMap<String, Object> params = new HashMap<>();
                    Product product = products.get(0);
                    params.put("id", product.getId());
                    params.put("name", product.getName());
                    params.put("price", product.getPrice());
                    params.put("picturePath", product.getPicturePath());


                    PageGenerator pageGenerator = PageGenerator.instance();
                    String page = pageGenerator.getPage("editProduct.html", params);

                    resp.setCharacterEncoding("UTF-8");

                    resp.getWriter().write(page);

                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            resp.sendRedirect("/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        Scanner sc = new Scanner(req.getParameter("price"));
        double price = sc.nextDouble();
        sc.close();
        String picturePath = req.getParameter("picturePath");
        int id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime addDate = LocalDateTime.now();

        productService.update(id, name, price, addDate, picturePath);

        String url = "/products/" + id;

        resp.sendRedirect(url);
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
