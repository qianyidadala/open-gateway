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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author IceFrog
 */
@Slf4j
public class FailSafeFaultRpcHandler extends AbstractFaultRpcHandler {

    public FailSafeFaultRpcHandler(RpcContext context) {
        super(context);
    }

    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {
        String url;
        ResponseEntity<Response> response = null;
        try {
            url = rpcContext.getProtocol() + rpcContext.getServiceId() + rpcContext.getUri();
            response = template.getForEntity(new URI(url), Response.class);
        } catch (Exception e) {
            log.error("invoke failed, serviceId:{}, uri:{}, error-message:{}",
                    rpcContext.getServiceId(), rpcContext.getUri(), e.getMessage(), e);
        }
        return response.getBody();
    }

    @Override
    public Response doInvokeAsync(RestTemplate template) throws RpcException {

        OpenGatewayConfig openGatewayConfig = rpcContext.getOpenGatewayContext().getRuntimeConfig().getOpenGatewayConfig();
        OpenGatewayThreadPoolExecute execute = OpenGatewayThreadPoolExecute.getInstance(openGatewayConfig.getCoreSize(), openGatewayConfig.getMaxPoolSize(), 1000, TimeUnit.MILLISECONDS);
        execute.execute(() -> {
            try {
                this.doInvoke(template);
            } catch (Exception e) {
                log.error("invoke failed, serviceId:{}, uri:{}, error-message:{}",
                        rpcContext.getServiceId(), rpcContext.getUri(), e.getMessage(), e);
            }
        });

        // Asynchronous processing is always in a successful state
        return new Response().success();
    }
}
