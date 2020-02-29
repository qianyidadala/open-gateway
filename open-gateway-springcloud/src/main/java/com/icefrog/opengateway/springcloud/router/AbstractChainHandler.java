/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.router;

import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;

/**
 * @author IceFrog
 */
public abstract class AbstractChainHandler {

    private AbstractChainHandler chainHandler = null;

    public void run(OpenGatewayContext context) {

        this.runAndNext(context);

        AbstractChainHandler nextHandler;
        if((nextHandler = this.getNextHandler()) != null) {
            nextHandler.run(context);
        }
    }

    protected abstract void runAndNext(OpenGatewayContext context);

    public AbstractChainHandler setNextHandler(AbstractChainHandler handler) {
        this.chainHandler = handler;
        return this;
    }

    public AbstractChainHandler getNextHandler() {
        return this.chainHandler;
    }
}
