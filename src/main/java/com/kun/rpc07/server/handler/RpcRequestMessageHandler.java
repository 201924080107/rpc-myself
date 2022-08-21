package com.kun.rpc07.server.handler;

import com.kun.rpc07.common.RPCRequest;
import com.kun.rpc07.common.RPCResponse;
import com.kun.rpc07.server.provider.ServiceProvider;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RPCRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequest message) {
        ctx.writeAndFlush(getResponse(message));
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
