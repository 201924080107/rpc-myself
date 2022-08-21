package com.serialize;

import com.kun.rpc06.common.User;
import com.kun.rpc08.serialize.java.ObjectSerializer;

public class Test {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        ObjectSerializer objectSerializer = new ObjectSerializer();
        byte[] serialize = objectSerializer.serialize(buffer);
        Buffer test1 = objectSerializer.deserialize(serialize, Buffer.class);
        test1.print();
    }
}
