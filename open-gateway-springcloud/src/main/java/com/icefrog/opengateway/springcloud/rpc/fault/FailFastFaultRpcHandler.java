/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc.fault;

import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.config.OpenGatewayConfig;
import com.icefrog.opengateway.springcloud.core.OpenGatewayThreadPoolExecute;
import com.icefrog.opengateway.springcloud.core.Response;
import com.icefrog.opengateway.springcloud.rpc.AbstractFaultRpcHandler;
import com.icefrog.opengateway.springcloud.rpc.RpcContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * A quick failure, a failed call, throws an exception and does not retry.
 * Implementations in asynchronous mode are consistent with synchronous implementations
 *
 * @see AbstractFaultRpcHandler
 * @see com.icefrog.opengateway.springcloud.rpc.Invoke
 * @author IceFrog
 */
public class FailFastFaultRpcHandler extends AbstractFaultRpcHandler {

    public FailFastFaultRpcHandler(RpcContext context) {
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
        OpenGatewayConfig openGatewayConfig = rpcContext.getOpenGatewayContext().getRuntimeConfig().getOpenGatewayConfig();
        OpenGatewayThreadPoolExecute execute = OpenGatewayThreadPoolExecute.getInstance(openGatewayConfig.getCoreSize(), openGatewayConfig.getMaxPoolSize(), 1000, TimeUnit.MILLISECONDS);
        execute.execute(() -> {
            try {
                doInvoke(template);
            } catch (RpcException e) {
                e.printStackTrace();
            }
        });
        return new Response().success();
    }
}
