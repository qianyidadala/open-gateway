/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.core.Response;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;


/**
 * @author IceFrog
 */
@Slf4j
@Setter
public class Rpc extends AbstractFaultRpcHandler {

    private AbstractFaultRpcHandler faultHandler;

    protected Rpc(RpcContext rpcContext) {
        super(rpcContext);
    }


    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {
        return null;
    }

    @Override
    public Response doInvokeAsync(RestTemplate template) throws RpcException {
        return null;
    }
}
