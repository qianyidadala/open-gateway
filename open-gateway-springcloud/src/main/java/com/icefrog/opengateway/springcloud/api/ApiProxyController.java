/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.api;

import com.icefrog.opengateway.common.base.FaultHandlerEnum;
import com.icefrog.opengateway.common.base.HttpProtocol;
import com.icefrog.opengateway.common.base.RpcException;
import com.icefrog.opengateway.common.web.ApiBaseController;
import com.icefrog.opengateway.springcloud.config.RuntimeConfig;
import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;
import com.icefrog.opengateway.springcloud.core.Response;
import com.icefrog.opengateway.springcloud.router.*;
import com.icefrog.opengateway.springcloud.rpc.RpcBuilder;
import com.icefrog.opengateway.springcloud.rpc.convertor.GatewayDefHttpMessageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
        messageConverters.add(new GatewayDefHttpMessageConvert());
        template.setMessageConverters(messageConverters);
        return template;
    }


    @RequestMapping("/**")
    public Response proxy(HttpServletRequest request) throws RpcException {

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
        Response response = RpcBuilder.newInstance()
                .setUri(request.getRequestURI(), true)
                .setProtocol(HttpProtocol.HTTP)
                .setAsync(false)
                .setRetryCount(runtimeConfig.getOpenGatewayConfig().getRetryCount())
                .setTimeout(runtimeConfig.getOpenGatewayConfig().getRetryCount())
                .setRequest(request)
                .setOpenGatewayContext(openGatewayContext)
                .getRpcHandler(FaultHandlerEnum.FAIL_OVER)
                .invoke(getRestTemplate());
        return response;
    }
}
