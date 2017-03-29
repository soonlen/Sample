package com.wzf.com.sample.volley.my;

import java.util.concurrent.FutureTask;

/**
 * Created by soonlen on 2017/3/29 14:45.
 * email wangzheng.fang@zte.com.cn
 */

public class volley {
    public static <T, M> void sendRequest(T requestData, String url,
                                          Class<M> response, IDataListener<M> listener) {
        IHttpListener iHttpListener = new JSONHttpListener<>(response, listener);
        HttpTask httpTask = new HttpTask(requestData, url, iHttpListener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask, null));
    }
}
