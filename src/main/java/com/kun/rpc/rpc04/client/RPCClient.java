package com.kun.rpc.rpc04.client;


import com.kun.rpc.rpc04.annotation.RpcReference;
import com.kun.rpc.rpc04.common.User;
import com.kun.rpc.rpc04.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

@Controller
@Slf4j
public class RPCClient {
    @RpcReference(name = "com.kun.rpc.rpc04.service.UserService")
    UserService userService;
    @GetMapping("/test/prc")
    @ResponseBody
    public User test() throws InterruptedException {
        User user = userService.getUserByUserId(1);
        log.error("{}",user);
        return user;
    }
}
