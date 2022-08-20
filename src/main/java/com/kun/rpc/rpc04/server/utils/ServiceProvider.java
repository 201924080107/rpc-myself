package com.kun.rpc.rpc04.server.utils;



import com.kun.rpc.rpc04.common.RPCRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 存放服务接口名与服务端对应的实现类
 * 服务启动时要暴露其相关的实现类
 * 根据request中的interface调用服务端中相关实现类
 */
public class ServiceProvider {
    /**
     * 一个实现类可能实现多个接口
     */
    private static Map<String, Object> map = new ConcurrentHashMap<>();
//    private static volatile ServiceProvider serviceProvider;
//
//    private ServiceProvider(){
//    }
//
//    public static ServiceProvider getInstance(){
//        if (serviceProvider == null){
//            synchronized (ServiceProvider.class){
//                if (serviceProvider == null){
//                    serviceProvider = new ServiceProvider();
//                }
//            }
//        }
//        return serviceProvider;
//    }
    public static Object getService(String name){
        return map.get(name);
    }

    public static void processor(String name,Object serviceImpl){
        Object o = map.get(name);
        if (null == o) {
            map.put(name,serviceImpl);
        }

        System.out.println(map);
    }
}
