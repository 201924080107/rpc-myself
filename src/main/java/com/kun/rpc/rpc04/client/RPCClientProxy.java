package com.kun.rpc.rpc04.client;


import com.kun.rpc.rpc04.annotation.RpcReference;
import com.kun.rpc.rpc04.common.RPCRequest;
import com.kun.rpc.rpc04.common.RPCResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class RPCClientProxy implements InvocationHandler {
    // 传入参数Service接口的class对象，反射封装成一个request
//    @Value("${rpc.host}")
    private static String host = "127.0.0.1";
//    @Value("${rpc.port}")
    private static int port = 8888;
    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        RpcReference annotation = proxy.getClass().getAnnotation(RpcReference.class);
//        System.out.println("!!!!!!!!!!!!!");
//        System.out.println(annotation.name());
        // request的构建，使用了lombok中的builder，代码简洁
        RPCRequest request = RPCRequest.builder().name(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();
        //数据传输
        RPCResponse response = IOClient.sendRequest(host, port, request);
        //System.out.println(response);
        return response.getData();
    }
    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
