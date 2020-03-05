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

import java.io.Serializable;

/**
 * @author IceFrog
 */
@Getter
@Setter
@EqualsAndHashCode
public class Response<T extends Serializable> {

    /** default error code of 1 */
    protected final int ERROR_CODE = 1;
    /** default success code of 0 */
    protected final int SUCCESS_CODE = 0;

    protected Integer code;

    protected String message;

    protected T data;

    public boolean isError() {
        return (code == null ? ERROR_CODE : code.intValue()) == ERROR_CODE;
    }

    public boolean isSuccess() {
        return !isError();
    }

    public Response<T> success() {
        return success(null, null);
    }

    public Response<T> success(String message) {
        return success(message, null);
    }

    public Response<T> success(String message, T data) {
        return generateResponseWithAll(SUCCESS_CODE, message, data);
    }

    public Response<T> error(String message) {
        return generateResponseWithAll(ERROR_CODE, message, null);
    }

    private Response<T> generateResponseWithAll(int code, String message, T data) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
