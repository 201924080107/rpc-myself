package com.rpc06.client;


import com.rpc06.annotation.RpcReference;
import com.rpc06.common.Blog;
import com.rpc06.common.User;
import com.rpc06.service.BlogService;
import com.rpc06.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class RPCClient {
    @RpcReference(name = "UserService")
    UserService userService;

    @RpcReference(name = "BlogService")
    BlogService blogService;
    @GetMapping("/test/rpc/user")
    @ResponseBody
    public User test() throws InterruptedException {
        User user = userService.getUserByUserId(1);
        return user;
    }

    @GetMapping("/test/rpc/blog")
    @ResponseBody
    public Blog test1() throws InterruptedException {
        Blog blog = blogService.getBlogById(1);
        return blog;
    }
}
