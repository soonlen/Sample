package com.wzf.com.sample.volley;

import static com.wzf.com.sample.volley.Request.Method.DEPRECATED_GET_OR_POST;

/**
 * Created by soonlen on 2017/3/28 11:00.
 * email wangzheng.fang@zte.com.cn
 */

public abstract class Request<T> implements Comparable<Request<T>>{

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    /**
     * Supported request methods.
     */
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }

    /**
     * Request method of this request.  Currently supports GET, POST, PUT, DELETE, HEAD, OPTIONS,
     * TRACE, and PATCH.
     */
    private final int mMethod;

    /**
     * URL of this request.
     */
    private final String mUrl;
    private RequestQueue mRequestQueue;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;

    /*public Request(String mUrl) {
        this(DEPRECATED_GET_OR_POST, mUrl);
    }

    public Request(int mMethod, String mUrl) {
        this.mMethod = mMethod;
        this.mUrl = mUrl;
    }*/
    public String getUrl() {
        return mUrl;
    }
    public Request(String mUrl, Response.Listener listener, Response.ErrorListener errorListener) {
        this.mMethod = DEPRECATED_GET_OR_POST;
        this.mUrl = mUrl;
        this.mListener = listener;
        this.mErrorListener = errorListener;
    }

    public Request setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
        return this;
    }
    @Override
    public int compareTo(Request<T> other) {
        return 0;
    }

    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);
    abstract protected void deliverResponse(T response);
}
