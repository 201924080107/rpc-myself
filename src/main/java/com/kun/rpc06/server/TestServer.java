package com.kun.rpc06.server;


import com.kun.rpc06.service.BlogService;
import com.kun.rpc06.service.BlogServiceImpl;
import com.kun.rpc06.service.UserService;
import com.kun.rpc06.service.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

//        Map<String, Object> serviceProvide = new HashMap<>();
//        serviceProvide.put("com.ganghuan.myRPCVersion2.service.UserService",userService);
//        serviceProvide.put("com.ganghuan.myRPCVersion2.service.BlogService",blogService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}