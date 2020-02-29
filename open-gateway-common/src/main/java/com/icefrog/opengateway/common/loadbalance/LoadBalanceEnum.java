/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.common.loadbalance;

import com.icefrog.opengateway.common.base.IEnum;

/**
 * @author IceFrog
 */
public enum LoadBalanceEnum implements IEnum<String> {

    HASH_LOAD_BALANCE("HASH_LOAD_BALANCE", "一致性HASH负载均衡"),

    RANDOM_LOAD_BALANCE("RANDOM_LOAD_BALANCE", "随机负载均衡"),

    WI_LOAD_BALANCE("WI_LOAD_BALANCE", "权重负载均衡");

    private String loadBalanceType;

    private String loadBalanceDesc;

    LoadBalanceEnum(String loadBalanceType, String loadBalanceDesc) {
        this.loadBalanceType = loadBalanceType;
        this.loadBalanceDesc = loadBalanceDesc;
    }

    @Override
    public String getValue() {
        return this.loadBalanceType;
    }

    @Override
    public String getDesc() {
        return this.loadBalanceDesc;
    }
}
