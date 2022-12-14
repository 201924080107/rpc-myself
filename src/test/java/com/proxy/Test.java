package com.proxy;

import com.proxy.service.UserService;
import com.proxy.service.impl.UserServiceImpl;
import com.proxy.service.TestService;

public class Test {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        TestService testService = new UserServiceImpl();

        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        proxyInvocationHandler.setUser(userService);
        UserService proxy = (UserService) proxyInvocationHandler.getProxy();
        System.out.println(proxy.getUserByUserId(1));

        ProxyInvocationHandler proxyInvocationHandler1 = new ProxyInvocationHandler();
        proxyInvocationHandler.setUser(testService);
        TestService proxy1 = (TestService) proxyInvocationHandler.getProxy();
        proxy1.print();
    }
}
