package com.study.shop.web.servlet;

import com.study.shop.entity.Product;
import com.study.shop.locator.ServiceLocator;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.Session;
import com.study.shop.service.ProductService;
import com.study.shop.util.CookieUtil;
import com.study.shop.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AllProductsServlet extends HttpServlet {
    private ProductService productService;
    private CookieUtil cookieUtil = new CookieUtil();
    private SecurityService securityService;

    public AllProductsServlet() {
        this.productService = ServiceLocator.getService(ProductService.class);
        this.securityService = ServiceLocator.getService(SecurityService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userToken = cookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);
        List<Product> products = productService.getAll();

        HashMap<String, Object> params = new HashMap<>();
        params.put("products", products);
        if(session != null) {
            params.put("count", session.getCart().getItemCount());
        }
        else {
            params.put("count", 0);
        }

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("products.html", params);

        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(page);

    }
}
