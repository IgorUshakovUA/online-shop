package com.study.shop.dao;

import com.study.shop.entity.User;

public interface UserDao {
    User getUser(String userName, String password);
}
