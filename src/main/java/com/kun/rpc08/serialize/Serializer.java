package com.kun.rpc08.serialize;


import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 序列化接口，所有序列化类都要实现这个接口
 */
@Service
public interface Serializer {
    byte[] serialize(Object obj) throws IOException;
    <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException;
}
