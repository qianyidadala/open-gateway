/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author IceFrog
 */
public class OpenGatewayThreadPoolExecute {

    public volatile static OpenGatewayThreadPoolExecute openGatewayThreadPoolExecute;

    private static ThreadPoolExecutor threadPoolExecutor;

    public static OpenGatewayThreadPoolExecute getInstance(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        synchronized (OpenGatewayThreadPoolExecute.class) {
            if(openGatewayThreadPoolExecute == null) {
                synchronized (OpenGatewayThreadPoolExecute.class) {
                    openGatewayThreadPoolExecute = new OpenGatewayThreadPoolExecute(corePoolSize, maximumPoolSize, keepAliveTime, unit);
                }
            }
        }
        return openGatewayThreadPoolExecute;
    }

    private OpenGatewayThreadPoolExecute(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit){
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime, unit,
                new ArrayBlockingQueue<>(maximumPoolSize));
    }

    public void execute(Runnable runnable) {
        if(threadPoolExecutor == null) {
            throw new NullPointerException("core threadPoolExecutor can not be null");
        }
        threadPoolExecutor.execute(runnable);
    }

    public int getPoolSize() {
        return this.threadPoolExecutor.getPoolSize();
    }
}
