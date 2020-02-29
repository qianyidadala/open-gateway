/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.common.loadbalance;

/**
 * LoadBalanceFactory
 * @author IceFrog
 */
public class LoadBalanceFactory {

    private LoadBalanceFactory(){}

    public static AbstractLoadBalance getLoadBalance(LoadBalanceEnum loadBalance) {
        return null;
    }
}
