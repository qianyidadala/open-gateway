/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author IceFrog
 */
@Setter
@Getter
@EqualsAndHashCode
@Deprecated
public abstract class AbstractRequest {

    protected int requestId;

    protected Map<String, String> requestParams;

    public AbstractRequest(int requestId) {
        this.requestId = requestId;
    }

}
