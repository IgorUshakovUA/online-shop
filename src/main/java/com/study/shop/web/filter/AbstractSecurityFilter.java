package com.study.shop.web.filter;

import com.study.shop.entity.User;
import com.study.shop.security.SecurityService;
import com.study.shop.security.entity.Session;
import com.study.shop.util.CookieUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractSecurityFilter implements Filter {
    private SecurityService securityService;
    private CookieUtil cookieUtil = new CookieUtil();

    public AbstractSecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        boolean isAuth = false;

        String userToken = cookieUtil.getCookieValue("user-session", httpServletRequest);
        if(userToken != null) {
            Session session = securityService.getSession(userToken);
            if (session != null) {
                if (hasRole(session.getUser())) {
                    isAuth = true;
                }
            }
        }

        if (isAuth) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }

    protected boolean hasRole(User user) {
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
