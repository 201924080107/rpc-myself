package com.kun.rpc05.client;

import com.alibaba.fastjson.JSON;
import com.kun.rpc05.server.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

@Slf4j
public class IOClient {
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) throws IOException {
//        Selector selector = Selector.open();
//        SocketChannel channel = SocketChannel.open();
//        channel.configureBlocking(false);
//        channel.register(selector, SelectionKey.OP_CONNECT + SelectionKey.OP_READ + SelectionKey.OP_WRITE);
//        channel.connect(new InetSocketAddress("127.0.0.1",8888));
//
//        while(true) {
//            selector.select();
//            Set<SelectionKey> selectionKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iterator = selectionKeys.iterator();
//            while(iterator.hasNext()){
//                SelectionKey key = iterator.next();
//                iterator.remove();
//                if (key.isConnectable()){
//                    log.debug("客户端连接成功，发送消息");
//                    String buffer = JSON.toJSONString(request);
//                    buffer = buffer + "\n";
//                    channel.write(Charset.defaultCharset().encode(buffer));
//                }else if (key.isReadable()){
//                    ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
//                    buffer.flip();
//                    channel.read(buffer);
//                    String s = Charset.defaultCharset().decode(buffer).toString();
//                    log.debug("接收到的消息是：{}",s);
//                    return null;
//                }
//            }
//        }

        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8888));
        String buffer = JSON.toJSONString(request);
        buffer = buffer + "#";
        log.info("发送的消息是{}",buffer);
        channel.write(Charset.defaultCharset().encode(buffer));
        return RPCResponse.builder().build();
    }

}
