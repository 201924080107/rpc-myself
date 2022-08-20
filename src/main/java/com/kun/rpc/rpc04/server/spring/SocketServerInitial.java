package com.kun.rpc.rpc04.server.spring;

import com.kun.rpc.rpc04.server.utils.WorkThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component // 交给spring管理
@Slf4j
public class SocketServerInitial implements ApplicationListener<ContextRefreshedEvent> {
 
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
 
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.error("SocketServerInitial onApplicationEvent - spring容器启动完成之后会触发ContextRefreshedEvent事件");
 
        ServerSocket serverSocket = null;
        try {
            // 创建port端口的服务端
            serverSocket = new ServerSocket(8888);
 
            while (true) {
                // 监听客户端请求
                Socket socket = serverSocket.accept();
                log.error("另外一个线程处理,此监听客户端请求的线程就不用阻塞在这里了");
                executorService.execute(new WorkThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
}