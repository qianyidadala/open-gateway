/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.HttpProtocol;
import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IceFrog
 */
@Data
public class RpcContext {

    private String serviceId;

    private String uri;

    private Integer retryCount;

    private boolean async;

    private Integer timeout;

    private String protocol;

    private HttpServletRequest request;

    private OpenGatewayContext openGatewayContext;

    public void setProtocol(HttpProtocol protocol) {
        this.protocol = protocol.getValue() + "://";
    }
}
