/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author IceFrog
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "opengateway")
public class OpenGatewayProxyRule {

    private Map<String, String> proxy = new HashMap<>();

}
