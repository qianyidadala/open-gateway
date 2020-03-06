/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.core;

import lombok.Data;

import java.util.Map;

/**
 * @author IceFrog
 */
@Data
public abstract class AbstractRequest {

    protected int requestId;

    protected Map<String, String> requestParams;

}
