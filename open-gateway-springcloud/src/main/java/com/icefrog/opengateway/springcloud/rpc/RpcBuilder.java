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

    public RpcBuilder setServiceId(String serviceId) {
        this.ensure().setServiceId(serviceId);
        return this;
    }

    public RpcBuilder setUri(String uri, boolean parseServiceId) {
        this.ensure().setUri(uri);
        if(parseServiceId) {
            uri = uri.replaceAll("/", ",");
            String serviceId = uri.split(",")[1];
            this.ensure().setServiceId(serviceId);
        }
        return this;
    }

    public RpcBuilder setTimeout(Integer timeout) {
        this.ensure().setTimeout(timeout);
        return this;
    }

    public RpcBuilder setRetryCount(Integer retryCount) {
        this.ensure().setRetryCount(retryCount);
        return this;
    }

    private Rpc ensure(){
        return this.init();
    }

}
