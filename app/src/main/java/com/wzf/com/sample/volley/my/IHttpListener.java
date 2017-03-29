package com.wzf.com.sample.volley.my;

import java.io.InputStream;

/**
 * Created by soonlen on 2017/3/29 13:44.
 * email wangzheng.fang@zte.com.cn
 */

public interface IHttpListener {
    void onSuccess(InputStream inputStream);
    void onError();
}
