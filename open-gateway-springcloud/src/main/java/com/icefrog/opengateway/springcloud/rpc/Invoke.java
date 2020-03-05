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
 * Remote Invoke
 *
 * @see com.icefrog.opengateway.common.base.RpcException
 * @see org.springframework.web.client.RestTemplate
 * @author IceFrog
 */
public interface Invoke<T extends Response> {

    /***
     * Remote Invoke with Spring Framework's RestTemplate(LoadBalance). base http connect
     * returned object must be extend to Response.
     * @param restTemplate org.springframework.web.client.RestTemplate
     * @return
     * @throws RpcException
     */
    T invoke(RestTemplate restTemplate) throws RpcException;

}
