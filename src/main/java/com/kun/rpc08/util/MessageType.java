package com.kun.rpc08.util;

public enum MessageType {
    RPCREQUEST(0),
    RPCRESPONSE(1);
    private int code;
    private MessageType(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
}
