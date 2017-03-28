package com.wzf.com.sample.volley;

/**
 * Created by soonlen on 2017/3/28 13:40.
 * email wangzheng.fang@zte.com.cn
 */

public interface Network {
    /**
     * Performs the specified request.
     *
     * @param request Request to process
     * @return A {@link NetworkResponse} with data and caching metadata; will never be null
     * @throws
     */
    public NetworkResponse performRequest(Request<?> request);
}
