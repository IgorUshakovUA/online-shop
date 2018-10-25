package com.study.shop.security.entity;

import com.study.shop.entity.Cart;
import com.study.shop.entity.User;

import java.time.LocalDateTime;

public class Session {
    private String token;
    private User user;
    private LocalDateTime expireTime;
    private Cart cart;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Cart getCart() { return cart; }

    public void setCart(Cart cart) { this.cart = cart; }

    @Override
    public String toString() {
        return "Session{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
