package com.rpc04.service.impl;


import com.rpc04.annotation.RpcService;
import com.rpc04.common.User;
import com.rpc04.service.UserService;

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
