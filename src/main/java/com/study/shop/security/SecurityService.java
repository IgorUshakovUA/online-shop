package com.study.shop.security;

import com.study.shop.entity.Cart;
import com.study.shop.entity.User;
import com.study.shop.security.entity.Session;
import com.study.shop.service.DefaultUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SecurityService {
    private static long TIMEOUT = 2;
    private List<Session> sessionList = new ArrayList<>();


    private DefaultUserService defaultUserService;

    public SecurityService() {

    }

    public SecurityService(DefaultUserService defaultUserService) {
        this.defaultUserService = defaultUserService;
    }

    public synchronized Session auth(String login, String password) {
        User user = defaultUserService.getUser(login, password);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            Session session = new Session();
            session.setUser(user);
            session.setToken(token);
            session.setExpireTime(LocalDateTime.now().plusHours(TIMEOUT));
            session.setCart(new Cart());

            sessionList.add(session);

            return session;
        }

        return null;
    }

    public synchronized Session getSession(String userToken) {
        Iterator<Session> iterator = sessionList.iterator();
        while(iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getToken().equals(userToken)) {
                if (session.getExpireTime().isBefore(LocalDateTime.now())) {
                    iterator.remove();
                    break;
                } else {
                    return session;
                }
            }
        }
        return null;
    }

    public void setDefaultUserService(DefaultUserService defaultUserService) {
        this.defaultUserService = defaultUserService;
    }
}
