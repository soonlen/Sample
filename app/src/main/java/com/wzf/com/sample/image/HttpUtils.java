package com.wzf.com.sample.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by soonlen on 2017/2/27 13:17.
 * email wangzheng.fang@zte.com.cn
 */

public class HttpUtils {

    public static InputStream getInputStream(String url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            return urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
