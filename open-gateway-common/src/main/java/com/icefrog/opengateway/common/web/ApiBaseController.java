/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.common.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author IceFrog
 */
public abstract class ApiBaseController {

    /***
     * 构建一个默认消息的成功状态返回对象
     * @return ApiResult
     */
    protected ApiResult success(){
        return this.buildApiResult(ApiResult.CODE_SUCCESS, ApiResult.SUCCESS_MESSAGE, null, null);
    }

    /***
     * 构建一个返回指定消息的成功状态返回对象
     * @param message 成功消息
     * @return ApiResult
     */
    protected ApiResult success(String message){
        return this.buildApiResult(ApiResult.CODE_SUCCESS, message, null, null);
    }

    /***
     * 构建一个返回指定消息和附加数据的成功状态返回对象
     * @param message 成功消息
     * @param data 附加数据
     * @return ApiResult
     */
    protected ApiResult success(String message, Map<String, Object> data){
        return this.buildApiResult(ApiResult.CODE_SUCCESS, message, data, null);
    }

    protected ApiResult success(Map<String, Object> data){
        return this.success(ApiResult.SUCCESS_MESSAGE, data);
    }

    protected ApiResult success(String key, Object value){
        Map<String, Object> data = new HashMap<>(1);
        data.put(key, value);
        return this.success(data);
    }

    /***
     * 构建一个默认消息的失败状态返回对象
     * @return ApiResult
     */
    protected ApiResult error(){
        return this.buildApiResult(ApiResult.CODE_FAILED, ApiResult.FAILED_MESSAGE, null, null);
    }

    /***
     * 构建一个返回指定消息的失败状态返回对象
     * @param message 失败消息
     * @return ApiResult
     */
    protected ApiResult error(String message){
        return this.buildApiResult(ApiResult.CODE_FAILED, message, null, null);
    }

    /***
     * 构建一个返回指定消息的失败状态返回对象
     * @param message 失败消息
     * @param t Throwable object
     * @return ApiResult
     */
    protected ApiResult error(String message, Throwable t){
        return this.buildApiResult(ApiResult.CODE_FAILED, message, null, t);
    }

    /***
     * 构建一个返回指定消息和附加数据的失败状态返回对象
     * @param message 失败消息
     * @param data 附加数据
     * @return ApiResult
     */
    protected ApiResult error(String message, Map<String, Object> data){
        return this.buildApiResult(ApiResult.CODE_FAILED, message, data, null);
    }

    /***
     * 构建一个返回指定消息和附加数据的失败状态返回对象
     * @param message 失败消息
     * @param data 附加数据
     * @param t Throwable object
     * @return ApiResult
     */
    protected ApiResult error(String message, Map<String, Object> data, Throwable t){
        return this.buildApiResult(ApiResult.CODE_FAILED, message, data, t);
    }

    private ApiResult buildApiResult(final int code, final String message, Map data, Throwable t){
        if(t != null){
            t.printStackTrace();
        }
        return new ApiResult().setCode(code).setMessage(message).setData(data);
    }
}
