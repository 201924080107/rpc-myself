package com.kun.rpc06.client;


import com.kun.rpc06.common.RPCRequest;
import com.kun.rpc06.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
