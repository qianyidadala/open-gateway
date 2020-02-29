/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author IceFrog
 */
@Getter
@Component
public class RuntimeConfig {

    @Autowired
    private OpenGatewayConfig openGatewayConfig;

    @Autowired
    private OpenGatewayProxyRule openGatewayProxyRule;

}
