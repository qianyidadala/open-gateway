/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.common.base;

/**
 * @author IceFrog
 */
public enum HttpProtocol implements IEnum<String> {
    HTTP("http", "HTTP"),
    HTTPS("https","HTTPS");

    private String value;

    private String desc;

    HttpProtocol(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
