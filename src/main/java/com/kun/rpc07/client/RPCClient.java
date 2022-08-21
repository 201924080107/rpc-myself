package com.kun.rpc07.client;

import com.kun.rpc07.common.RPCRequest;
import com.kun.rpc07.common.RPCResponse;
import org.springframework.stereotype.Service;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
