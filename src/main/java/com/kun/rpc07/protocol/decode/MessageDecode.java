package com.kun.rpc07.protocol.decode;

import com.kun.rpc07.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class MessageDecode extends ByteToMessageDecoder {
    private Serializer serializer;
    private Class<?> aClass;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializerAlgorithm = byteBuf.readByte(); // 0 或 1
        byte messageType = byteBuf.readByte(); // 0,1,2...
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        Object object = serializer.deserialize(bytes, aClass);
        list.add(object);
        log.debug("反序列化成功");
    }
}
