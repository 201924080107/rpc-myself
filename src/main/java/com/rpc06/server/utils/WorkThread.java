package com.rpc06.server.utils;


import com.rpc06.common.RPCRequest;
import com.rpc06.common.RPCResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 这里负责解析得到的request请求，执行服务方法，返回给客户端
 * 1. 从request得到interfaceName 2. 根据interfaceName在serviceProvide Map中获取服务端的实现类
 * 3. 从request中得到方法名，参数， 利用反射执行服务中的方法 4. 得到结果，封装成response，写入socket
 */
@AllArgsConstructor
@Slf4j
public class WorkThread implements Runnable{
    private Socket socket;
    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            // 读取客户端传过来的request
            RPCRequest request = (RPCRequest) ois.readObject();
            log.info("服务端收到{}",request);
            // 反射调用服务方法获得返回值
            RPCResponse response = getResponse(request);
            log.info("服务端返回{}",response);
            //写入到客户端
            oos.writeObject(response);
            oos.flush();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            log.error("服务端IO错误");
        }
    }

    private RPCResponse getResponse(RPCRequest request){
        // 得到服务名
        String name = request.getName();
        // 得到服务端相应服务实现类
        Object service = ServiceProvider.getService(name);
        // 反射调用方法
        Method method = null;
        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            log.debug("RPC方法执行错误");
            return RPCResponse.fail();
        }
    }
}
