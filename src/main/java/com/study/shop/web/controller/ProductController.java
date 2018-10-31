package com.study.shop.web.controller;

import com.study.shop.entity.CartProduct;
import com.study.shop.entity.Product;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.Session;
import com.study.shop.service.ProductService;
import com.study.shop.util.CookieUtil;
import com.study.shop.util.ResourceUtil;
import com.study.shop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private SecurityService securityService;


    @ResponseBody
    @RequestMapping(path = {"/", "/products"}, method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String getAll(HttpServletRequest req, HttpServletResponse resp) {
        String userToken = CookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);
        List<Product> products = productService.getAll();

        HashMap<String, Object> params = new HashMap<>();
        params.put("products", products);
        if (session != null) {
            params.put("count", session.getCart().getItemCount());
        } else {
            params.put("count", 0);
        }

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("products.html", params);

        return page;
    }

    @ResponseBody
    @RequestMapping(path = {"/assets/*","/assets/img/*","/assets/css/*"}, method = RequestMethod.GET)
    public byte[] getAssets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestURI = req.getRequestURI();

        ServletOutputStream outputStream = resp.getOutputStream();
        InputStream inputStream = ResourceUtil.getResourceAsStream(requestURI.substring(1));

        byte[] result = new byte[inputStream.available()];
        inputStream.read(result);

        return result;
    }


    @ResponseBody
    @RequestMapping(path = "/product/add", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String addProductPage() {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addProduct.html");

        return page;
    }

    @RequestMapping(path = "/product/add", method = RequestMethod.POST)
    public String addProduct(@RequestParam String name, @RequestParam double price, @RequestParam String picturePath) {
        int id = productService.add(name, price, picturePath);

        System.out.println("Product with id: " + id + " was created!");
        return "redirect:/products";
    }

    @ResponseBody
    @RequestMapping(path="/product/edit/{id}", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String editProductPage(@PathVariable int id) {
        try {
            List<Product> products = productService.getById(id);
            HashMap<String, Object> params = new HashMap<>();
            Product product = products.get(0);
            params.put("id", product.getId());
            params.put("name", product.getName());
            params.put("price", String.format("%.2f",product.getPrice()).replace(",","."));
            params.put("picturePath", product.getPicturePath());

            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("editProduct.html", params);

            return page;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/products";
    }

    @RequestMapping(path = "/product/edit", method = RequestMethod.POST)
    public String editProduct(@RequestParam int id, @RequestParam String name, @RequestParam double price, @RequestParam String picturePath) {
        LocalDateTime addDate = LocalDateTime.now();

        productService.update(id, name, price, addDate, picturePath);

        return "redirect:/products";
    }

    @RequestMapping(path="/product/delete/{id}", method=RequestMethod.GET)
    public String deleteProduct(@PathVariable int id) {
        productService.delete(id);

        return "redirect:/products";
    }

    @ResponseBody
    @RequestMapping(path="/cart", method = RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String getCart(HttpServletRequest req, HttpServletResponse resp) {
        String userToken = CookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);

        List<CartProduct> products = productService.getByCart(session.getCart());
        for (CartProduct cartProduct : products) {
            cartProduct.setCount(session.getCart().getCountById(cartProduct.getProduct().getId()));
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("products", products);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("cart.html", params);

        return page;
    }

    @ResponseBody
    @RequestMapping(path="/login", method=RequestMethod.GET, produces = "text/html; charset=UTF-8")
    public String getLoginPage() {
        PageGenerator pageGenerator = PageGenerator.instance();

        HashMap<String, Object> params = new HashMap<>();
        params.put("message", "");
        String page = pageGenerator.getPage("login.html", params);

        return page;
    }

    @RequestMapping(path="/login",method = RequestMethod.POST)
    public String login(@RequestParam String name, @RequestParam String password, HttpServletResponse resp) {
        Session session = securityService.auth(name, password);

        if (session != null) {
            CookieUtil.setCookie("user-session", session.getToken(),resp);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(path="/cart/add/{id}", method=RequestMethod.GET)
    public String addToCart(@PathVariable int id, HttpServletRequest req) {
        String userToken = CookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);

        session.getCart().addProduct(id,1);

        return "redirect:/products";
    }

    @RequestMapping(path="/cart/delete/{id}", method = RequestMethod.GET)
    public String deleteFromCart(@PathVariable int id, HttpServletRequest req) {
        String userToken = CookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);

        session.getCart().deleteProduct(id);

        return "redirect:/cart";
    }

    @RequestMapping(path="/cart/plus/{id}", method = RequestMethod.GET)
    public String plusOneFromCart(@PathVariable int id, HttpServletRequest req) {
        String userToken = CookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);

        session.getCart().addProduct(id,1);

        return "redirect:/cart";
    }

    @RequestMapping(path="/cart/minus/{id}", method = RequestMethod.GET)
    public String minusOneFromCart(@PathVariable int id, HttpServletRequest req) {
        String userToken = CookieUtil.getCookieValue("user-session", req);
        Session session = securityService.getSession(userToken);

        session.getCart().decreaseCount(id);

        return "redirect:/cart";
    }
}
