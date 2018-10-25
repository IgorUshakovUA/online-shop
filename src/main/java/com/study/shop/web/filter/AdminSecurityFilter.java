package com.study.shop.web.filter;

import com.study.shop.entity.User;
import com.study.shop.locator.ServiceLocator;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.UserRole;

public class AdminSecurityFilter extends AbstractSecurityFilter {

    public AdminSecurityFilter(){
        super(ServiceLocator.getService(SecurityService.class));
    }

    @Override
    protected boolean hasRole(User user) {
        return user.getUserRole() == UserRole.ADMIN;
    }
}
