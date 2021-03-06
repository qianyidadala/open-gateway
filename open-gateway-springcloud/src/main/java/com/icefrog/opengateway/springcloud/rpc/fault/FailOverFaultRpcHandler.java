/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc.fault;

import com.alibaba.fastjson.JSONObject;
import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.config.OpenGatewayConfig;
import com.icefrog.opengateway.springcloud.core.OpenGatewayThreadPoolExecute;
import com.icefrog.opengateway.springcloud.core.Response;
import com.icefrog.opengateway.springcloud.rpc.AbstractFaultRpcHandler;
import com.icefrog.opengateway.springcloud.rpc.RpcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Failover is retried, depending on the number of retries configured.
 * In an asynchronous implementation, this processing is moved to an asynchronous thread to complete,
 * and a response marked correctly is returned
 * The actual load of the failover is left to the balancing policy of the registry itself
 *
 * @see AbstractFaultRpcHandler
 * @see com.icefrog.opengateway.springcloud.rpc.Invoke
 * @author Ice Frog
 */
@Slf4j
public class FailOverFaultRpcHandler extends AbstractFaultRpcHandler {

    public FailOverFaultRpcHandler(RpcContext context) {
        super(context);
    }

    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {
        String url;
        Response response = null;
        int currentRetryCount = 0;
        while(currentRetryCount < rpcContext.getRetryCount()) {
            try {
                currentRetryCount++;
                url = rpcContext.getProtocol() + rpcContext.getServiceId() + rpcContext.getUri();

                // The actual load of the failover is left to the balancing policy of the registry itself
                ResponseEntity<String> entity = template.getForEntity(new URI(url), String.class);
                String body = entity.getBody();
                response = new Response<HashMap>().success("successfully",
                        (HashMap) JSONObject.parseObject(body).getInnerMap());
                break;
            } catch (Exception e) {
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
        return response;
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
