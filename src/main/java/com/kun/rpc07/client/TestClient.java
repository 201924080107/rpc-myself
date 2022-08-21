package com.kun.rpc07.client;

import com.kun.rpc07.common.Blog;
import com.kun.rpc07.service.BlogService;
import com.kun.rpc07.service.UserService;
import com.kun.rpc07.annotation.RpcReference;
import com.kun.rpc07.common.User;
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
