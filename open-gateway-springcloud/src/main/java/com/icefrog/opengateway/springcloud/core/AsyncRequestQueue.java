/*
 * Copyright 2020 icefrog All rights reserved.
 *
 * @since 1.8
 * @author: icefrog.su@qq.com
 */

package com.icefrog.opengateway.springcloud.core;

import org.apache.commons.lang.ArrayUtils;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author IceFrog
 */
public class AsyncRequestQueue {

    private final static int DEFAULT_INIT_SIZE = 3;

    private BlockingQueue<AsyncRequest> ASYNC_REQUESTED_QUEUE;

    public static AsyncRequestQueue newInstance(int initSize) {
        AsyncRequestQueue queue = new AsyncRequestQueue();
        queue.setAsyncRequestedQueue(new ArrayBlockingQueue<AsyncRequest>(initSize));
        return queue;
    }

    public AsyncRequestQueue newInstance(List<AsyncRequest> asyncRequests) {
        if(asyncRequests == null) {
            return this.newInstance(DEFAULT_INIT_SIZE);
        }
        return newInstance((AsyncRequest[]) asyncRequests.toArray());
    }

    public AsyncRequestQueue newInstance(AsyncRequest... asyncRequests) {
        if(ArrayUtils.isEmpty(asyncRequests)) {
            throw new IllegalArgumentException("asyncRequests can not be null or empty");
        }
        AsyncRequestQueue queue = new AsyncRequestQueue();
        ArrayBlockingQueue<AsyncRequest> asyncRequestsQueue = new ArrayBlockingQueue<>(asyncRequests.length);
        for(int i = 0; i < asyncRequests.length; i++) {
            asyncRequestsQueue.offer(asyncRequests[i]);
        }
        queue.setAsyncRequestedQueue(asyncRequestsQueue);
        return queue;
    }

    public AsyncRequest pollAsyncRequest() {
        return getAsyncRequestedQueue().poll();
    }


    public void setAsyncRequestedQueue(BlockingQueue<AsyncRequest> asyncRequestedQueue) {
        this.ASYNC_REQUESTED_QUEUE = asyncRequestedQueue;
    }

    public BlockingQueue<AsyncRequest> getAsyncRequestedQueue() {
        return this.ASYNC_REQUESTED_QUEUE;
    }
}
