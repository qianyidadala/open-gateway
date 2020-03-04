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
public enum FaultHandlerEnum implements IEnum<String> {

    FAIL_BACK("FailBack", "失败自动恢复"),
    FAIL_FAST("FailFast", "快速失败"),
    FAIL_OVER("FailOver", "故障转移"),
    FAIL_SAFE("FailSafe", "安全失败");

    FaultHandlerEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;

    private String desc;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
