/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.FaultHandlerEnum;
import com.icefrog.opengateway.common.base.HttpProtocol;
import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IceFrog
 */
public class RpcBuilder {

    private AbstractFaultRpcHandler faultRpcHandler;

    private RpcContext rpcContext;

    public static RpcBuilder newInstance() {
        return new RpcBuilder();
    }

    public AbstractFaultRpcHandler getRpcHandler(FaultHandlerEnum faultHandlerType) {
        return FaultHandlerFactory.newInstance(rpcContext).getFaultHandler(faultHandlerType);
    }

    private RpcContext ensure() {
        if(rpcContext == null) {
            rpcContext = new RpcContext();
        }
        return rpcContext;
    }

    public RpcBuilder setServiceId(String serviceId) {
        ensure().setServiceId(serviceId);
        return this;
    }

    public RpcBuilder setUri(String uri, boolean parseServiceId) {
        String serviceId = null;
        if(parseServiceId) {
            String uriBak = uri.replaceAll("/", ",");
            serviceId = uriBak.split(",")[1];
            this.ensure().setServiceId(serviceId);
        }
        this.ensure().setUri(uri.replaceAll(serviceId, ""));
        return this;
    }

    public RpcBuilder setRetryCount(Integer retryCount) {
        ensure().setRetryCount(retryCount);
        return this;
    }

    public RpcBuilder setAsync(boolean isAsync) {
        ensure().setAsync(isAsync);
        return this;
    }

    public RpcBuilder setTimeout(Integer timeout) {
        ensure().setTimeout(timeout);
        return this;
    }

    public RpcBuilder setProtocol(HttpProtocol protocol) {
        ensure().setProtocol(protocol);
        return this;
    }

    public RpcBuilder setRequest(HttpServletRequest request) {
        ensure().setRequest(request);
        return this;
    }

    public RpcBuilder setOpenGatewayContext(OpenGatewayContext openGatewayContext) {
        ensure().setOpenGatewayContext(openGatewayContext);
        return this;
    }

}
