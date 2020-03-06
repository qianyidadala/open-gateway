/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc.fault;

import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.core.Response;
import com.icefrog.opengateway.springcloud.rpc.AbstractFaultRpcHandler;
import com.icefrog.opengateway.springcloud.rpc.RpcContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Failover is retried, depending on the number of retries configured.
 * In an asynchronous implementation, this processing is moved to an asynchronous thread to complete,
 * and a response marked correctly is returned
 * The actual load of the failover is left to the balancing policy of the registry itself
 *
 * @see AbstractFaultRpcHandler
 * @see com.icefrog.opengateway.springcloud.rpc.Invoke
 * @author IceFrog
 */
@Slf4j
public class FailOverFaultRpcHandler extends AbstractFaultRpcHandler {

    public FailOverFaultRpcHandler(RpcContext context) {
        super(context);
    }

    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {
        String url;
        ResponseEntity<Response> response = null;
        int currentRetryCount = 0;
        while(currentRetryCount < rpcContext.getRetryCount()) {
            try {
                currentRetryCount++;
                url = rpcContext.getProtocol() + rpcContext.getServiceId() + rpcContext.getUri();
                // The actual load of the failover is left to the balancing policy of the registry itself
                response = template.getForEntity(new URI(url), Response.class);
                break;
            } catch (Exception e) {
                if (currentRetryCount == rpcContext.getRetryCount()) {
                    log.error("rpc invoke failed! count to:%d", currentRetryCount);
                    break;
                }
                if (log.isWarnEnabled()) {
                    log.warn("rpc invoke failed! serviceId:%s, retry:%d, current:%d, message:%s",
                            rpcContext.getServiceId(), rpcContext.getRetryCount(), currentRetryCount, e.getMessage());
                }
            }
        }
        return response.getBody();
    }

    @Override
    public Response doInvokeAsync(RestTemplate template) throws RpcException {
        return null;
    }

}
