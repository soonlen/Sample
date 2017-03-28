package com.wzf.com.sample.volley;

import android.os.Process;

import java.util.concurrent.BlockingQueue;

/**
 * Created by soonlen on 2017/3/28 13:07.
 * email wangzheng.fang@zte.com.cn
 */

public class NetworkDispatcher extends Thread {

    private final BlockingQueue<Request<?>> mQueue;
    /** The network interface for processing requests. */
    /**
     * Used for telling us to die.
     */
    private volatile boolean mQuit = false;
    private final Network mNetwork;
    private final ResponseDelivery mDelivery;

    public NetworkDispatcher(BlockingQueue<Request<?>> queue,
                             Network network, ResponseDelivery delivery) {
        mQueue = queue;
        mNetwork = network;
        this.mDelivery = delivery;
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Request<?> request;
        while (true) {
            try {
                request = mQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (mQuit) {
                    return;
                }
                continue;
            }
            NetworkResponse networkResponse = mNetwork.performRequest(request);
            Response<?> response = request.parseNetworkResponse(networkResponse);
            mDelivery.postResponse(request, response);
        }
    }
}
