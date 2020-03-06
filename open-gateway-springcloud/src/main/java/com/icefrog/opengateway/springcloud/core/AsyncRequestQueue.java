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
 * Asynchronous request memory queue, stored using ArrayBlockingQueue
 *
 * @see AsyncRequest
 * @see BlockingQueue
 * @see ArrayBlockingQueue
 * @author IceFrog
 */
public class AsyncRequestQueue {

    private final static int DEFAULT_INIT_SIZE = 3;

    private BlockingQueue<AsyncRequest> ASYNC_REQUESTED_QUEUE;

    /***
     * Build an empty queue,and size with initSize
     * @param initSize init size
     * @return Empty queue
     */
    public static AsyncRequestQueue newInstance(int initSize) {
        AsyncRequestQueue queue = new AsyncRequestQueue();
        queue.setAsyncRequestedQueue(new ArrayBlockingQueue<AsyncRequest>(initSize));
        return queue;
    }

    /***
     * Build a queue object and specify the collection of elements to be pressed in.
     * Note that the final size of the collection is determined by the current initialization size,
     * and subsequent enqueue operations may fail because the space is full
     *
     * @param asyncRequests AsyncRequest List
     * @return AsyncRequestQueue new instance
     */
    public AsyncRequestQueue newInstance(List<AsyncRequest> asyncRequests) {
        if(asyncRequests == null) {
            return this.newInstance(DEFAULT_INIT_SIZE);
        }
        return newInstance((AsyncRequest[]) asyncRequests.toArray());
    }

    /***
     * Build a queue object and specify the collection of elements to be pressed in.
     * Note that the final size of the collection is determined by the current initialization size,
     * and subsequent enqueue operations may fail because the space is full
     *
     * @param asyncRequests AsyncRequest Array
     * @return AsyncRequestQueue new instance
     */
    public AsyncRequestQueue newInstance(AsyncRequest... asyncRequests) {
        if(ArrayUtils.isEmpty(asyncRequests)) {
            throw new IllegalArgumentException("asyncRequests can not be null or empty");
        }
        AsyncRequestQueue queue = new AsyncRequestQueue();
        ArrayBlockingQueue<AsyncRequest> asyncRequestsQueue = new ArrayBlockingQueue<>(asyncRequests.length);
        for(int i = 0; i < asyncRequests.length; i++) {
            // Do not consider the result of the queue
            asyncRequestsQueue.offer(asyncRequests[i]);
        }
        queue.setAsyncRequestedQueue(asyncRequestsQueue);
        return queue;
    }

    /***
     * Pushes an AsyncRequest into a queue in a non-blocking manner.False is returned when the queue is full
     * @param asyncRequest AsyncRequest
     * @return False is returned when the queue is full
     */
    public boolean offerAsyncRequest(AsyncRequest asyncRequest) {
        return getAsyncRequestedQueue().offer(asyncRequest);
    }

    /***
     * Retrieves and deletes the element that returns the header of the queue, returns null if there is none
     * @return AsyncRequest,maybe returned null.
     */
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
