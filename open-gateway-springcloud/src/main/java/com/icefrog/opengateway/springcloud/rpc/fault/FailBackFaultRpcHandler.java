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
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author IceFrog
 */
@Slf4j
public class FailBackFaultRpcHandler extends AbstractFaultRpcHandler {

    public FailBackFaultRpcHandler(RpcContext context) {
        super(context);
    }

    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {

        try {
            return _doInvoke(template);
        } catch (Exception e) {
            OpenGatewayConfig openGatewayConfig = rpcContext.getOpenGatewayContext().getRuntimeConfig().getOpenGatewayConfig();
            OpenGatewayThreadPoolExecute execute = OpenGatewayThreadPoolExecute.getInstance(openGatewayConfig.getCoreSize(), openGatewayConfig.getMaxPoolSize(), 1000, TimeUnit.MILLISECONDS);
            execute.execute(() -> {
                int currentRetryCount = 0;
                while(currentRetryCount < rpcContext.getRetryCount()) {
                    try {
                        currentRetryCount++;
                        _doInvoke(template);
                    } catch (RpcException ex) {
                        if (currentRetryCount == rpcContext.getRetryCount()) {
                            log.error("rpc invoke failed! count to:{}", currentRetryCount);
                            break;
                        }
                        if (log.isWarnEnabled()) {
                            log.warn("rpc invoke failed! serviceId:{}, retry:{}, current:{}, message:{}",
                                    rpcContext.getServiceId(), rpcContext.getRetryCount(), currentRetryCount, e.getMessage());
                        }
                    }
                }
            });
        }
        return new Response().error("Failed auto recovery policy started, asynchronous retry...");
    }

    @Override
    public Response doInvokeAsync(RestTemplate template) throws RpcException {
        return doInvoke(template);
    }

    private Response _doInvoke(RestTemplate template) throws RpcException {
        try {
            String url = rpcContext.getProtocol() + rpcContext.getServiceId() + rpcContext.getUri();
            return template.getForEntity(new URI(url), Response.class).getBody();
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }
}
