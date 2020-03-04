/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.HttpProtocol;

/**
 * @author IceFrog
 */
public class RpcBuilder {

    private Rpc rpc;

    public Rpc init() {
        if(rpc == null) {
            rpc = new Rpc();
        }
        return rpc;
    }

    public Rpc getRpc() {
        return this.rpc;
    }



}
