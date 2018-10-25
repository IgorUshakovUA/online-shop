package com.study.shop.dao;

import com.study.shop.entity.Cart;
import com.study.shop.entity.CartProduct;
import com.study.shop.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductDao {
    List<Product> getAll();

    List<Product> getById(int id);

    List<CartProduct> getByCart(Cart cart);

    void update(int id, String name, double price, LocalDateTime addTime, String picturePath);

    void delete(int id);

    int add(String name, double price, String picturePath);
}
