package com.study.shop.web.controller;

import com.study.shop.entity.User;
import com.study.shop.security.entity.UserRole;

public class AdminAuthInterceptor extends AbstractAuthInterceptor {

    public AdminAuthInterceptor() {

    }

    @Override
    protected boolean hasRole(User user) {
        return user.getUserRole() == UserRole.ADMIN;
    }
}
