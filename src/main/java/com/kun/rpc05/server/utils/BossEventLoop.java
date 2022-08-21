package com.kun.rpc05.server.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BossEventLoop implements Runnable{
    private Selector boss;
    private WorkerEventLoop[] workers;
    private volatile boolean start = false;
    AtomicInteger index = new AtomicInteger();

    public void register(int workerNumber) throws IOException {
        if(!start){
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress(8888));
            channel.configureBlocking(false);
            boss = Selector.open();
            SelectionKey register = channel.register(boss, 0, null);
            register.interestOps(SelectionKey.OP_ACCEPT);
            workers = initEventLoops(workerNumber);
            new Thread(this,"boss").start();
            start = true;
        }
    }

    public WorkerEventLoop[] initEventLoops(int workerNumber){
        WorkerEventLoop[] workerEventLoops = new WorkerEventLoop[workerNumber];
        for (int i = 0; i < workerEventLoops.length; i++){
            workerEventLoops[i] = new WorkerEventLoop(i);
        }
        return workerEventLoops;
    }

    @Override
    public void run() {
        while (true) {
            try {
                boss.select();
                Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()){
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        log.debug("接受来自{}连接",accept.getRemoteAddress());
                        workers[index.getAndDecrement() % workers.length].register(accept);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}















































