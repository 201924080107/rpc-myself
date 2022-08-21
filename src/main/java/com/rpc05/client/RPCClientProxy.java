package com.rpc05.client;


import com.rpc05.server.RPCResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RPCClientProxy implements InvocationHandler {
    private static String host = "127.0.0.1";
    private static int port = 8888;
    //记录request中名字和接口对应关系
    public static Map<String,String> map = new ConcurrentHashMap<>();


    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().name(map.get(method.getDeclaringClass().getName()))
                .methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();
        RPCResponse response = IOClient.sendRequest(host, port, request);
        //System.out.println(response);
        return response.getData();
    }
    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
