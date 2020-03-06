/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.common.base;

/**
 * @author IceFrog
 */
public class RpcException extends Exception {

    public RpcException(String message) {
        super(message);
    }

    public RpcException(Throwable t) {
        super(t);
    }

    public RpcException(String message, Throwable t) {
        super(message, t);
    }
}
