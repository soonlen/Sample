package com.wzf.com.sample.volley.my;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by soonlen on 2017/3/29 13:50.
 * email wangzheng.fang@zte.com.cn
 */

public class ThreadPoolManager {

    private static ThreadPoolManager instance;
    private ThreadPoolExecutor poolExecutor;
    private LinkedBlockingDeque<Future<?>> services = new LinkedBlockingDeque<>();

    private ThreadPoolManager() {
        poolExecutor = new ThreadPoolExecutor(4, 10, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        poolExecutor.execute(runnable);
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null)
                    instance = new ThreadPoolManager();
            }
        }
        return instance;
    }

    /**
     * 外部调用接口
     * @param task
     * @param <T>
     */
    public <T> void excute(FutureTask<T> task) {
        if (task != null) {
            try {
                services.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 阻塞式队列
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                FutureTask task = null;
                try {
                    task = (FutureTask) services.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null) {
                    poolExecutor.execute(task);
                }
            }
        }
    };
    /**
     * 拒绝策略
     */
    RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                services.put(new FutureTask<Object>(r, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
