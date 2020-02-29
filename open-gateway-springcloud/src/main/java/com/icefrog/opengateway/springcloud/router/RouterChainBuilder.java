/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.router;

import com.icefrog.opengateway.springcloud.core.OpenGatewayContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author IceFrog
 */
@Slf4j
public class RouterChainBuilder {

    private List<AbstractChainHandler> chainHandlers;

    private OpenGatewayContext context;

    private boolean running = true;

    public RouterChainBuilder() {
        chainHandlers = new LinkedList<>();
    }

    public RouterChainBuilder addHandler(@NonNull AbstractChainHandler handler) {
        this.chainHandlers.add(handler);
        return this;
    }

    public RouterChainBuilder setContext(@NonNull OpenGatewayContext context) {
        this.context = context;
        return this;
    }

    public boolean run() {
        try {
            for (AbstractChainHandler chainHandler : chainHandlers) {
                if(!running) {
                    break;
                }
                chainHandler.run(this.context);
            }
        } catch (Exception e) {
            log.error("Run the RouterChainBuilder's handler list failed. ", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public void close() {
        this.running = false;
    }
}
