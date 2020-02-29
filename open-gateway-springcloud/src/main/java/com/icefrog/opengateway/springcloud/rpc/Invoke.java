/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.springcloud.core.Response;
import org.springframework.web.client.RestTemplate;

/**
 * @author IceFrog
 */
public interface Invoke<T extends Response> {

    T invoke(RestTemplate restTemplate);

}
