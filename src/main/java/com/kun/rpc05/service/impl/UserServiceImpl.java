package com.kun.rpc05.service.impl;


import com.kun.rpc05.annotation.RpcService;
import com.kun.rpc05.common.User;
import com.kun.rpc05.service.UserService;

@RpcService(name = "UserService")
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        // 模拟从数据库中取用户的行为
        User user = User.builder().id(id).userName("he2121").sex(true).build();
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        return 1;
    }
}
