package com.wzf.com.sample.volley;

import android.content.Context;

/**
 * Created by soonlen on 2017/3/28 11:00.
 * email wangzheng.fang@zte.com.cn
 */

public class Volley {
    /* public static RequestQueue newRequestQueue(Context context){
         return this(context,null);
     }*/
    public static RequestQueue newRequestQueue(Context context) {
        Network network = new BasicNetwork();
        RequestQueue requestQueue = new RequestQueue(network);
        requestQueue.start();
        return requestQueue;
    }

}
