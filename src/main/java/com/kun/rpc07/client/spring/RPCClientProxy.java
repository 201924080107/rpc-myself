package com.kun.rpc07.client.spring;


import com.kun.rpc07.client.RPCClient;
import com.kun.rpc07.client.impl.NettyRPCClient;
import com.kun.rpc07.common.RPCRequest;
import com.kun.rpc07.common.RPCResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RPCClientProxy implements InvocationHandler {
    private static String host = "127.0.0.1";
    private static int port = 8888;
    //记录request中名字和接口对应关系
    public static Map<String,String> map = new ConcurrentHashMap<>();
    private static RPCClient client = new NettyRPCClient("127.0.0.1",8888);

    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().name(map.get(method.getDeclaringClass().getName()))
                .methodName(method.getName())
                .params(args).paramsTypes(method.getParameterTypes()).build();
        log.debug("发送的请求是{}",request);
        RPCResponse rpcResponse = client.sendRequest(request);
        return rpcResponse.getData();
    }
    public <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
