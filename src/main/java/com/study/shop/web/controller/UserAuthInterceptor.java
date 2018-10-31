package com.study.shop.web.controller;

import com.study.shop.entity.User;
import com.study.shop.security.entity.UserRole;

public class UserAuthInterceptor extends AbstractAuthInterceptor {

    public UserAuthInterceptor() {

    }

    @Override
    protected boolean hasRole(User user) {
        UserRole userRole = user.getUserRole();
        return userRole == UserRole.ADMIN || userRole == UserRole.USER;
    }
}
