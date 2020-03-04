/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

/**
 * @author IceFrog
 */
public abstract class AbstractFaultHandler {

    protected RpcContext rpcContext;

    protected AbstractFaultHandler(RpcContext rpcContext) {
        this.rpcContext = rpcContext;
    }
}
