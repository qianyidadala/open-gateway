/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.HttpProtocol;
import lombok.Data;

/**
 * @author IceFrog
 */
@Data
public class RpcContext {

    private String serviceId;

    private String uri;

    private Integer retryCount;

    private boolean isAsync;

    private Integer timeout;

    private String protocol;

    public void setProtocol(HttpProtocol protocol) {
        this.protocol = protocol.getValue() + "://";
    }
}
