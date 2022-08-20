package com.kun.rpc.proxy.service.impl;


import com.kun.rpc.proxy.service.UserService;
import com.kun.rpc.proxy.entity.User;
import com.kun.rpc.proxy.service.TestService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService, TestService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("客户端查询了"+id+"的用户");
        // 模拟从数据库中取用户的行为
        Random random = new Random();
        User user = User.builder().userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }
    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入数据成功："+user);
        return 1;
    }

    @Override
    public void print() {
        System.out.println("测试代理");
    }
}
