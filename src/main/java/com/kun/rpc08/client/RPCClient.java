package com.kun.rpc08.client;

import com.kun.rpc08.common.RPCRequest;
import com.kun.rpc08.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
