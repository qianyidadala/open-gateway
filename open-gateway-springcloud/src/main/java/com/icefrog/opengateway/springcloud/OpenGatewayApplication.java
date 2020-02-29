/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * OpenGatewayApplication for Spring Cloud
 *
 * @author IceFrog
 */
@Slf4j
//@EnableEurekaClient
@SpringBootApplication
public class OpenGatewayApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(OpenGatewayApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("OpenGatewayApplication for SpringCloud Start Successful.");
    }
}
