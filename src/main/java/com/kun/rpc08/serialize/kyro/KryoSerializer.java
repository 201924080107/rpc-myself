package com.kun.rpc08.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.kun.rpc08.common.Blog;
import com.kun.rpc08.common.RPCRequest;
import com.kun.rpc08.common.RPCResponse;
import com.kun.rpc08.common.User;
import com.kun.rpc08.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kryo serialization class, Kryo serialization efficiency is very high, but only compatible with Java language
 *
 * @author shuang.kou
 * @createTime 2020年05月13日 19:29:00
 */
@Slf4j
public class KryoSerializer implements Serializer {

    /**
     * Because Kryo is not thread safe. So, use ThreadLocal to store Kryo objects
     */
    public static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RPCRequest.class);
        kryo.register(RPCResponse.class);
        kryo.register(Object[].class);
        kryo.register(Class[].class);
        kryo.register(Class.class);
        kryo.register(User.class);
        kryo.register(Blog.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) throws IOException {
        log.debug("序列化开始");
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, obj);
            log.debug("序列化成功");
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            log.error("kryo序列化错误");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        log.debug("反序列化开始");
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // byte->Object:从byte数组中反序列化出对对象
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            log.error("kryo反序列化错误");
            e.printStackTrace();
            throw e;
        }
    }

}
