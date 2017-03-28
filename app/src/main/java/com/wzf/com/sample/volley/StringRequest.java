package com.wzf.com.sample.volley;

/**
 * Created by soonlen on 2017/3/28 13:34.
 * email wangzheng.fang@zte.com.cn
 */

public class StringRequest extends Request<String> {

    private final Response.Listener<String> mListener;

    public StringRequest(String mUrl, Response.Listener listener, Response.ErrorListener errorListener) {
        super(mUrl, listener, errorListener);
        this.mListener=listener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String parsed = null;
        if (response != null && response.data != null) {
            parsed = new String(response.data);
        }
        return Response.success(parsed);
    }

    @Override
    protected void deliverResponse(String response) {
        this.mListener.response(response);
    }

    @Override
    public int compareTo(Request<String> another) {
        return 0;
    }
}
