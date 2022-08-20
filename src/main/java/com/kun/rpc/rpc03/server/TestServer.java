package com.kun.rpc.rpc03.server;


import com.kun.rpc.rpc03.server.service.RPCServer;
import com.kun.rpc.rpc03.server.utils.ServiceProvider;
import com.kun.rpc.rpc03.server.utils.ThreadPoolRPCRPCServer;
import com.kun.rpc.rpc03.service.BlogService;
import com.kun.rpc.rpc03.service.UserService;
import com.kun.rpc.rpc03.service.impl.BlogServiceImpl;
import com.kun.rpc.rpc03.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new ThreadPoolRPCRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}