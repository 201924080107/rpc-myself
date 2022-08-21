package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    private Object target;

    public void setUser(Object target) {
        this.target = target;
    }

    @Override
    //代理对象 -- 调用的方法 -- 方法的参数
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //System.out.println(o.getClass() + "==" + method.getName() + "==" + objects.length);
        //调用方法的对象 -- 参数
        //静态方法使用null 非静态我们需要使用 具体对象
        Object invoke = method.invoke(target,objects);
        return invoke;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),target.getClass().getInterfaces(),this);
    }
}
