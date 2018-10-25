package com.study.shop.service;

import com.study.shop.dao.UserDao;
import com.study.shop.entity.User;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    @Override
    public User getUser(String userName, String password) {
        return userDao.getUser(userName, password);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
