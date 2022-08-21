package com.bean.service;


import com.bean.annotation.RpcService;
import com.bean.common.User;

@RpcService
public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);

    Integer insertUserId(User user);
}
