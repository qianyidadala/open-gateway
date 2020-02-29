/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.core;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author IceFrog
 */
@Data
public class RequestContext {

    private Object header;

    private HttpServletRequest request;

}
