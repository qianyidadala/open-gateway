/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.config;

import com.icefrog.opengateway.springcloud.rpc.FaultHandleEnum;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author IceFrog
 */
@Getter
@Component
public class OpenGatewayConfig {

    @Value("${opengateway.retryCount:0}")
    private Integer retryCount;

    @Value("${opengateway.timeout:1000}")
    private Integer timeout;

    @Value("${opengateway.coreSize:30}")
    private Integer coreSize;

    @Value("${opengateway.maxPoolSize:50}")
    private Integer maxPoolSize;

    @Value("${opengateway.faultHandle:FailOver}")
    private String faultHandle;

    public FaultHandleEnum getFaultHandler() {
        FaultHandleEnum faultHandleEnum;
        switch (faultHandle) {
            case "FailFast":
                faultHandleEnum = FaultHandleEnum.FAIL_FAST;
                break;
            case "FailBack":
                faultHandleEnum = FaultHandleEnum.FAIL_BACK;
                break;
            case "FailSafe":
                faultHandleEnum = FaultHandleEnum.FAIL_SAFE;
                break;
            case "FailOver":
            default:
                faultHandleEnum = FaultHandleEnum.FAIL_OVER;
                break;
        }
        return faultHandleEnum;
    }
}
