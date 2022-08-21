package com.rpc05.server.spring;

import com.rpc05.server.utils.BossEventLoop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;

@Component // 交给spring管理
@Slf4j
public class SocketServerInitial implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("RPC服务器开始进行监听");
        try {
            new BossEventLoop().register(2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
}