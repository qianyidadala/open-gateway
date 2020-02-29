/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.core;

import com.icefrog.opengateway.springcloud.config.RuntimeConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author IceFrog
 */
@Data
@Component
public class OpenGatewayContext {

    ThreadLocal<RequestContext> requestContext = new ThreadLocal<>();

    @Autowired
    private RuntimeConfig runtimeConfig;

}
