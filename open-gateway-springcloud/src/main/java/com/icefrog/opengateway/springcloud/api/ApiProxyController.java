/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.api;

import com.icefrog.opengateway.common.web.ApiBaseController;
import com.icefrog.opengateway.springcloud.config.RuntimeConfig;
import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;
import com.icefrog.opengateway.springcloud.router.*;
import com.icefrog.opengateway.springcloud.rpc.RpcBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IceFrog
 */
@Configuration
@RestController
@RequestMapping("/")
public class ApiProxyController extends ApiBaseController {

    @Autowired
    private OpenGatewayContext openGatewayContext;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @RequestMapping("/**")
    public void proxy(HttpServletRequest request) {

        // 初始化路由链
        RouterChainBuilder routerChainBuilder = new RouterChainBuilder();
        routerChainBuilder
                .addHandler(new LimiterRouterHandler())
                .addHandler(new TokenRouterHandler())
                .addHandler(new SecurityRouterHandler())
                .addHandler(new DecipherRouterHandler())
                .addHandler(new LoadBalanceRouterHandler())
                .setContext(null)
                .run();

        // 初始化调用链
        RuntimeConfig runtimeConfig = openGatewayContext.getRuntimeConfig();
        RpcBuilder rpcBuilder = new RpcBuilder();
        rpcBuilder
                .setUri(request.getRequestURI(), true)
                .setRetryCount(runtimeConfig.getOpenGatewayConfig().getRetryCount())
                .setTimeout(runtimeConfig.getOpenGatewayConfig().getRetryCount())
                .getRpc()
                .invoke(getRestTemplate());
    }
}
