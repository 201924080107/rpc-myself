package com.rpc04.client;

import com.rpc04.common.RPCRequest;
import com.rpc04.common.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class IOClient {
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            log.info("客户端调用远程服务{}中的{}方法",request.getName(),request.getMethodName());
            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            log.info("客户端收到的消息是{}",response);
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }

    }

}
