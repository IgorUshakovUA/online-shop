package com.study.shop.service;

import com.study.shop.dao.ProductDao;
import com.study.shop.entity.Cart;
import com.study.shop.entity.CartProduct;
import com.study.shop.entity.Product;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DefaultProductService implements ProductService {
    private ProductDao productDao;

    @Override
    public List<Product> getAll() {
        List<Product> products = productDao.getAll();
        Collections.sort(products, Comparator.comparingInt(Product::getId));
        return products;
    }

    @Override
    public List<Product> getById(int id) {
        List<Product> products = productDao.getById(id);
        return products;
    }

    @Override
    public List<CartProduct> getByCart(Cart cart) {
        List<CartProduct> products = productDao.getByCart(cart);
        return products;
    }

    @Override
    public void update(int id, String name, double price, LocalDateTime addTime, String picturePath) {
        productDao.update(id, name, price, addTime, picturePath);
    }

    @Override
    public void delete(int id) {
        productDao.delete(id);
    }

    @Override
    public int add(String name, double price, String picturePath) {
        return productDao.add(name, price, picturePath);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
