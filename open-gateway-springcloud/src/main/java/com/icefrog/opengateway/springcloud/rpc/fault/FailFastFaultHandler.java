/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc.fault;

import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.springcloud.core.Response;
import com.icefrog.opengateway.springcloud.rpc.AbstractFaultHandler;
import com.icefrog.opengateway.springcloud.rpc.RpcContext;
import org.springframework.web.client.RestTemplate;

/**
 * @author IceFrog
 */
public class FailFastFaultHandler extends AbstractFaultHandler {

    public FailFastFaultHandler(RpcContext context) {
        super(context);
    }

    @Override
    public Response doInvoke(RestTemplate template) throws RpcException {
        return null;
    }

    @Override
    public Response doInvokeAsync(RestTemplate template) throws RpcException {
        return null;
    }


}
