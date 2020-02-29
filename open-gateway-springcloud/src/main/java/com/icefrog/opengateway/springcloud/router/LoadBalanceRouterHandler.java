/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.router;

import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;

/**
 * 负载均衡链
 * @author IceFrog
 */
public class LoadBalanceRouterHandler extends AbstractChainHandler {

    @Override
    protected void runAndNext(OpenGatewayContext context) {
        System.out.println("LoadBalanceRouterHandler");
    }
}
