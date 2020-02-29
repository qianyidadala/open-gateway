/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.springcloud.core.Response;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

/**
 * @author IceFrog
 */
@Setter
public class Rpc implements Invoke<Response> {

    private String serviceId;

    private String uri;

    private Integer retryCount;

    private Integer timeout;

    @Override
    public Response invoke(RestTemplate restTemplate) {
        // TODO send http request with restTemplate
        return null;
    }
}
