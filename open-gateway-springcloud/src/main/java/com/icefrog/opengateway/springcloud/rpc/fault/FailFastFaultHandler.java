/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc.fault;

import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.core.Response;
import com.icefrog.opengateway.springcloud.rpc.AbstractFaultHandler;
import com.icefrog.opengateway.springcloud.rpc.RpcContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * A quick failure, a failed call, throws an exception and does not retry.
 * Implementations in asynchronous mode are consistent with synchronous implementations
 *
 * @see com.icefrog.opengateway.springcloud.rpc.AbstractFaultHandler
 * @see com.icefrog.opengateway.springcloud.rpc.Invoke
 * @author IceFrog
 */
public class FailFastFaultHandler extends AbstractFaultHandler {

    public FailFastFaultHandler(RpcContext context) {
        super(context);
    }

    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {
        String url;
        ResponseEntity<Response> response;
        try {
            url = rpcContext.getProtocol() + rpcContext.getServiceId() + rpcContext.getUri();
            response = template.getForEntity(new URI(url), Response.class);
        } catch (Exception e) {
            throw new RpcException(String.format("invoke service %s failed, trigger the FailFast strategy! error-message:%s",
                    rpcContext.getServiceId(), e.getMessage()),
                    e.getCause());
        }
        return response.getBody();
    }

    @Override
    public Response doInvokeAsync(RestTemplate template) throws RpcException {
        return this.doInvoke(template);
    }
}
