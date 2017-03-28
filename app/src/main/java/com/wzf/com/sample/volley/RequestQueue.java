package com.wzf.com.sample.volley;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by soonlen on 2017/3/28 11:01.
 * email wangzheng.fang@zte.com.cn
 */

public class RequestQueue {

    private PriorityBlockingQueue<Request<?>> mNetWorkQueue = new PriorityBlockingQueue<>();
    /**
     * Network interface for performing requests.
     */
    private final Network mNetwork;

    /**
     * Response delivery mechanism.
     */
    private final ResponseDelivery mDelivery;

    /**
     * The network dispatchers.
     */
    private NetworkDispatcher[] mDispatchers;

    /**
     * Creates the worker pool. Processing will not begin until {@link #start()} is called.
     *
     * @param network        A Network interface for performing HTTP requests
     * @param threadPoolSize Number of network dispatcher threads to create
     * @param delivery       A ResponseDelivery interface for posting responses and errors
     */
    public RequestQueue(Network network, int threadPoolSize,
                        ResponseDelivery delivery) {
        mNetwork = network;
        mDispatchers = new NetworkDispatcher[threadPoolSize];
        mDelivery = delivery;
    }

    /**
     * @param network        A Network interface for performing HTTP requests
     * @param threadPoolSize Number of network dispatcher threads to create
     */
    public RequestQueue(Network network, int threadPoolSize) {
        this(network, threadPoolSize,
                new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    /**
     * Creates the worker pool. Processing will not begin until {@link #start()} is called.
     *
     * @param network A Network interface for performing HTTP requests
     */
    public RequestQueue(Network network) {
        this(network, 4);
    }

    public Request<?> add(Request<?> request) {
        request.setmRequestQueue(this);
        mNetWorkQueue.add(request);
        return request;
    }

    public void start() {
        stop();
        for (int i = 0; i < mDispatchers.length; i++) {
            NetworkDispatcher networkDispatchers = new NetworkDispatcher(mNetWorkQueue, mNetwork, mDelivery);
            mDispatchers[i] = networkDispatchers;
            networkDispatchers.start();
        }
    }

    private void stop() {
        for (int i = 0; i < mDispatchers.length; i++) {
            if (mDispatchers[i] != null)
                mDispatchers[i].quit();
        }
    }
}
