package com.study.shop.service;

import com.study.shop.dao.AuthDao;

public class DefaultAuthService implements AuthService {
    AuthDao authDao;

    @Override
    public String login(String userName, String password) {
        return authDao.login(userName,password);
    }

    public void setAuthDao(AuthDao authDao) {
        this.authDao = authDao;
    }
}
