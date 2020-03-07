/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.core.Response;
import org.springframework.web.client.RestTemplate;

/**
 * @author IceFrog
 */
public abstract class AbstractFaultRpcHandler implements Invoke<Response> {

    protected final RpcContext rpcContext;

    protected AbstractFaultRpcHandler(RpcContext rpcContext) {
        if(rpcContext == null) {
            throw new NullPointerException("rpcContext can not be null");
        }
        this.rpcContext = rpcContext;
    }

    @Override
    public Response invoke(RestTemplate restTemplate) throws RpcException {
        if(restTemplate == null) {
            throw new NullPointerException("restTemplate can not be null");
        }

        Response response;
        if(!rpcContext.isAsync()) {
            response = this.doInvoke(restTemplate);
        } else {
            response = this.doInvokeAsync(restTemplate);
        }
        return response;
    }

    protected abstract Response doInvoke(RestTemplate template) throws RpcException;

    protected abstract Response doInvokeAsync(RestTemplate template) throws RpcException;
}
