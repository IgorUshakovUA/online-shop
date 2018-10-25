package com.study.shop.service;

import com.study.shop.entity.User;

public interface UserService {
    User getUser(String userName, String password);
}
