/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.api;

import com.icefrog.opengateway.common.web.ApiBaseController;
import com.icefrog.opengateway.springcloud.config.OpenGatewayConfig;
import com.icefrog.opengateway.springcloud.config.OpenGatewayProxyRule;
import com.icefrog.opengateway.springcloud.config.RuntimeConfig;
import com.icefrog.opengateway.springcloud.router.*;
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
    private RuntimeConfig runtimeConfig;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @RequestMapping("/**")
    public void proxy(HttpServletRequest request) {
        String url = request.getRequestURI();

        OpenGatewayConfig openGatewayConfig = runtimeConfig.getOpenGatewayConfig();
        OpenGatewayProxyRule openGatewayProxyRule = runtimeConfig.getOpenGatewayProxyRule();


        // 初始化责任链
        RouterChainBuilder routerChainBuilder = new RouterChainBuilder();
        routerChainBuilder
                .addRouterHandler(new LimiterRouterHandler())
                .addRouterHandler(new TokenRouterHandler())
                .addRouterHandler(new SecurityRouterHandler())
                .addRouterHandler(new DecipherRouterHandler())
                .addRouterHandler(new LoadBalanceRouterHandler())
                .setContext(null)
                .run();

        // 初始化调用链

         System.out.println("do proxy :" + url);

    }

}
