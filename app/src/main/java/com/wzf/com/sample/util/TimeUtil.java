package com.wzf.com.sample.util;


/**
 * Created by soonlen on 2017/2/27 17:06.
 * email wangzheng.fang@zte.com.cn
 */

public class TimeUtil {

    static long time = -1;

    public static void start() {
        time = System.currentTimeMillis();
    }

    public static void end() {
        if (time != -1) {
            L.e("cosumer time is " + (System.currentTimeMillis() - time));
            time = -1;
        }
    }
}
