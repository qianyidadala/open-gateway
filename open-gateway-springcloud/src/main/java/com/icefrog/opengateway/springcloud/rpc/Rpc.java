/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.HttpProtocol;
import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.core.Response;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author IceFrog
 */
@Slf4j
@Setter
public class Rpc extends AbstractFaultHandler implements Invoke<Response> {

    private AbstractFaultHandler faultHandler;

    @Override
    public Response invoke(RestTemplate restTemplate) throws RpcException {
        int currentIndex = 0;
        while(currentIndex < rpcContext.getRetryCount()) {
            try {
                currentIndex++;
                ResponseEntity<String> response = restTemplate.getForEntity(new URI(rpcContext.getProtocol() + rpcContext.getServiceId() + rpcContext.getUri()), String.class);
                String respStr = response.getBody();
                System.out.println(respStr);
                break;
            } catch (Exception e) {
                if(currentIndex == rpcContext.getRetryCount()) {
                    log.error("rpc invoke failed! count to:%d", currentIndex);
                    break;
                }
                if(log.isWarnEnabled()) {
                    log.warn("rpc invoke failed! retry:%d, current:%d, message:%s", rpcContext.getRetryCount(), currentIndex, e.getMessage());
                }
            }
        }
        return null;
    }
}
