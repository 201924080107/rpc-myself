package com.rpc03.server;


import com.rpc03.server.service.RPCServer;
import com.rpc03.server.utils.ServiceProvider;
import com.rpc03.server.utils.ThreadPoolRPCRPCServer;
import com.rpc03.service.BlogService;
import com.rpc03.service.UserService;
import com.rpc03.service.impl.BlogServiceImpl;
import com.rpc03.service.impl.UserServiceImpl;

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