package com.study.shop.web.controller;

import com.study.shop.entity.User;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.Session;
import com.study.shop.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean isAuth = false;

        String userToken = CookieUtil.getCookieValue("user-session", request);
        if(userToken != null) {
            Session session = securityService.getSession(userToken);
            if (session != null) {
                if (hasRole(session.getUser())) {
                    isAuth = true;
                }
            }
        }

        if (isAuth) {
            return true;
        } else {
            response.sendRedirect("/login");
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    protected boolean hasRole(User user) {
        return false;
    }
}
