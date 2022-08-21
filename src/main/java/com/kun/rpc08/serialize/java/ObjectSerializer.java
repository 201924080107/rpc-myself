package com.kun.rpc08.serialize.java;

import com.kun.rpc07.serialize.Serializer;

import java.io.*;

public class ObjectSerializer implements Serializer {

    // 利用java IO 对象 -> 字节数组
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    // 字节数组 -> 对象
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz.cast(obj);
    }
}