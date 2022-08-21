package com.rpc06.service;


import com.rpc06.common.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);

    Integer insertUserId(User user);
}
