package com.rpc05.server.utils;

import com.alibaba.fastjson.JSON;
import com.rpc05.client.RPCRequest;
import com.rpc05.server.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
@Slf4j
public class WorkerEventLoop implements Runnable{
    private Selector worker;
    private volatile  boolean start = false;
    private int index;
    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    public WorkerEventLoop(int i){
        index = i;
    }
    public void register(SocketChannel socketChannel) throws IOException {
        //worker线程是由boss线程启动的，但是run方法是在work线程中进行运行的，而将socketChannel的注册是在我们的boss线程中
        //进行的，那么就会造成一个问题就是我们没办法控制这两个方法运行的先后顺序，如果一旦select方法比register先运行，因为没有
        //注册事件所以就会阻塞状态，而且因为select selector是并发安全的
        //所以channel的register方法会请求一个锁对象而另一个线程的selector的select方法也会请求这个方法
        //并阻塞等待至少一个channel状态响应，所以就进入了死锁。

        //如果我们能够将这两个方法移动到一个线程中执行，就可以控制两者的运行顺序了，放在boss线程中肯定不合适，我们
        //放在work线程中，但是因为socketChannel是在主线程中获取的，所以如何完成他们之间的消息传递呢
        //我们可以选用ConcurrentLinkedQueue

        //我们使用队列存放了我们要注册的这个动作，目前还有个问题，run方法执行了，select会阻塞住
        //那么怎么打破这种阻塞让他去执行下面的方法，我们有两种选择
        //wakeup  如果另一个线程在调用select()或select(long)方法时被阻止，则该调用将立即返回
        //也就是只要我们添加了任务，为了让任务进行注册，我们就将正在阻塞的select，打断
        if(!start){
            worker = Selector.open();
            new Thread(this,"worker-" + index).start();
            start = true;
        }

        tasks.add(() -> {
            try {
                SelectionKey key = socketChannel.register(worker,0,null);
                key.interestOps(SelectionKey.OP_READ + SelectionKey.OP_WRITE);
            } catch (ClosedChannelException e) {
                throw new RuntimeException(e);
            }
        });

        worker.wakeup();
    }
    @Override
    public void run() {
        while(true) {
            try {
                //为什么task执行不放在上面，因为第一次执行run的时候并没有添加任务所以不太行
                worker.select();
                Runnable task = tasks.poll();
                if (task != null){
                    task.run();
                }
                Set<SelectionKey> keys = worker.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                if (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            int read = socketChannel.read(buffer);
                            if (read == -1){
                                key.cancel();
                                socketChannel.close();
                            }else{
//                                buffer.flip(); //两次flip是的limit为0！！！！
                                splitAndResponse(buffer,socketChannel);
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                            key.cancel();
                            socketChannel.close();
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void splitAndResponse(ByteBuffer source,SocketChannel channel) {
        source.flip();
        int oldLimit = source.limit();
        for (int i = 0; i < source.limit(); i++) {
            if(source.get(i) == '#'){
                int length = i + 1 - source.position();
                ByteBuffer buffer = ByteBuffer.allocate(length);
                source.limit(i + 1);
                buffer.put(source);
                buffer.flip();
                String s = Charset.defaultCharset().decode(buffer).toString();
                String substring = s.substring(0, s.length() - 1);
                log.info("{}",substring);
                RPCRequest rpcRequest = JSON.parseObject(substring, RPCRequest.class);
                source.limit(oldLimit);
            }
        }
        source.compact();
    }

    private RPCResponse getResponse(RPCRequest request){
        // 得到服务名
        String name = request.getName();
        // 得到服务端相应服务实现类
        Object service = ServiceProvider.getService(name);
        // 反射调用方法
        Method method = null;
        try {
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            log.debug("RPC方法执行错误");
            return RPCResponse.fail();
        }
    }
}

























