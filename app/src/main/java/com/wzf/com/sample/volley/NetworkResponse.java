package com.wzf.com.sample.volley;

import java.util.Collections;
import java.util.Map;

/**
 * Created by soonlen on 2017/3/28 13:32.
 * email wangzheng.fang@zte.com.cn
 */

public class NetworkResponse {
    /**
     * The HTTP status code.
     */
    public final int statusCode;

    /**
     * Raw data from this response.
     */
    public final byte[] data;

    /**
     * Response headers.
     */
    public final Map<String, String> headers;

    /**
     * True if the server returned a 304 (Not Modified).
     */
    public final boolean notModified;

    public NetworkResponse(int statusCode, byte[] data,
                           Map<String, String> headers, boolean notModified) {
        super();
        this.statusCode = statusCode;
        this.data = data;
        this.headers = headers;
        this.notModified = notModified;
    }

    public NetworkResponse(byte[] data) {
        this(200, data, Collections.<String, String>emptyMap(), false);
    }

    public NetworkResponse(byte[] data, Map<String, String> headers) {
        this(200, data, headers, false);
    }
}
