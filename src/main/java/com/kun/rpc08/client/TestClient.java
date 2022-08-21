package com.kun.rpc08.client;

import com.kun.rpc08.annotation.RpcReference;
import com.kun.rpc08.common.Blog;
import com.kun.rpc08.common.User;
import com.kun.rpc08.service.BlogService;
import com.kun.rpc08.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestClient {
    @RpcReference(name = "UserService")
    UserService userService;
    @RpcReference(name = "BlogService")
    BlogService blogService;
    @GetMapping("/test/netty/rpc/user")
    @ResponseBody
    public User getUser(){
        return userService.getUserByUserId(1);
    }
    @GetMapping("/test/netty/rpc/blog")
    @ResponseBody
    public Blog getBlog(){
        return blogService.getBlogById(1);
    }
}
