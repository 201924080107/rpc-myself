package com.kun.rpc08.server.spring;

import com.kun.rpc08.common.RPCRequest;
import com.kun.rpc08.common.RPCResponse;
import com.kun.rpc08.protocol.ProcotolFrameDecoder;
import com.kun.rpc08.protocol.decode.MessageDecode;
import com.kun.rpc08.protocol.encode.MessageEncode;
import com.kun.rpc08.serialize.Serializer;
import com.kun.rpc08.serialize.kyro.KryoSerializer;
import com.kun.rpc08.server.handler.RpcRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component // 交给spring管理
@Slf4j
public class SocketServerInitial implements ApplicationListener<ContextRefreshedEvent> {
 
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
 
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        Serializer serializer = new KryoSerializer();
        RpcRequestMessageHandler RPC_HANDLER = new RpcRequestMessageHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。
                    // TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProcotolFrameDecoder());
                            ch.pipeline().addLast(LOGGING_HANDLER);
                            ch.pipeline().addLast(new MessageDecode(serializer, RPCRequest.class));
                            ch.pipeline().addLast(new MessageEncode(serializer, RPCResponse.class));
                            ch.pipeline().addLast(RPC_HANDLER);
                        }
                    });
            Channel channel = serverBootstrap.bind(8888).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
 
}