package com.kun.rpc07.common;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message implements Serializable {
    public abstract int getMessageType();
}
