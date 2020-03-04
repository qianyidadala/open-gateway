/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.rpc;

import com.icefrog.opengateway.common.base.FaultHandlerEnum;
import com.icefrog.opengateway.springcloud.rpc.fault.*;

/**
 * Fault Handler Factory. build FaultHandler with FaultHandlerEnum
 *
 * @see FaultHandlerEnum Fault Handler Type
 * @see AbstractFaultHandler Abstracted Faule Handler
 * @see FailBackFaultHandler Fail Back Fault Handler
 * @see FailFastFaultHandler Fail Fast Fault Handler
 * @see FailOverFaultHandler Fail Over Fault Handler
 * @see FailSafeFaultHandler Fail Safe Fault Handler
 * @author IceFrog
 */
public class FaultHandlerFactory {

    private RpcContext context = null;

    private AbstractFaultHandler currentHandler = null;

    public FaultHandlerFactory(RpcContext context) {
        this.context = context;
    }

    public FaultHandlerFactory() {
        this(null);
    }

    public void setContext(RpcContext context) {
        this.context = context;
    }

    public RpcContext getContext() {
        return context;
    }

    public boolean hasRpcContext() {
        return this.getContext() != null;
    }

    public static FaultHandlerFactory newInstance(RpcContext context) {
        return new FaultHandlerFactory(context);
    }

    public static FaultHandlerFactory newInstance() {
        return new FaultHandlerFactory(null);
    }

    public AbstractFaultHandler getCurrentHandler() {
        return currentHandler;
    }

    /***
     * Build FaultHandler instance
     * if faultHandlerType is null, throws the java.lang.NullPointerException.
     * Supported: FaultHandlerEnum.FAIL_OVER, FaultHandlerEnum.FAIL_BACK, FaultHandlerEnum.FAIL_FAST, FaultHandlerEnum.FAIL_SAFE
     *
     * @param faultHandlerType FaultHandler instance type
     * @return AbstractFaultHandler instance,build by faultHandler type
     */
    public AbstractFaultHandler getFaultHandler(FaultHandlerEnum faultHandlerType) {
        if(faultHandlerType == null) {
            throw new NullPointerException("faultHandlerType can not be null");
        }

        switch (faultHandlerType) {
            case FAIL_OVER:
                return getFailOverHandler();
            case FAIL_BACK:
                return getFailBackHandler();
            case FAIL_FAST:
                return getFailFastHandler();
            case FAIL_SAFE:
                return getFailSafeHandler();
            default:
                return getDefault();
        }
    }

    public AbstractFaultHandler getDefault() {
        return getFailOverHandler();
    }

    public AbstractFaultHandler getFailBackHandler() {
        AbstractFaultHandler faultHandler = new FailBackFaultHandler(getContext());
        return faultHandler;
    }

    public AbstractFaultHandler getFailFastHandler() {

        return null;
    }

    public AbstractFaultHandler getFailOverHandler() {

        return null;
    }

    public AbstractFaultHandler getFailSafeHandler() {

        return null;
    }

}
