package com.study.shop;

import com.study.shop.locator.ServiceLocator;
import com.study.shop.web.filter.AdminSecurityFilter;
import com.study.shop.web.filter.UserSecurityFilter;
import com.study.shop.web.servlet.*;
import com.ushakov.ioc.context.classpath.ClassPathApplicationContext;
import com.ushakov.ioc.entity.Bean;
import com.ushakov.ioc.entity.BeanDefinition;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

public class Starter {

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ClassPathApplicationContext applicationContext = new ClassPathApplicationContext("my_web_app.xml");

        for (BeanDefinition beanDefinition : applicationContext.getBeanDefinitions()) {
            if (beanDefinition.getValueDependency().get("type").equalsIgnoreCase("servlet")) {
                HttpServlet servlet = (HttpServlet) applicationContext.getBean(Class.forName(beanDefinition.getClassName()));
                String[] urls = beanDefinition.getValueDependency().get("urls").split("\\|");
                for (String url : urls) {
                    context.addServlet(new ServletHolder(servlet), url);
                }

            } else if (beanDefinition.getValueDependency().get("type").equalsIgnoreCase("filter")) {
                Filter filter = (Filter)applicationContext.getBean(Class.forName(beanDefinition.getClassName()));
                String[] urls = beanDefinition.getValueDependency().get("urls").split("\\|");
                for (String url : urls) {
                    context.addFilter(new FilterHolder(filter), url, EnumSet.of(DispatcherType.REQUEST));
                }
            }
        }


        Server server = new Server(Integer.parseInt(ServiceLocator.getParameterValue("port")));

        server.setHandler(context);

        server.start();
    }


}
