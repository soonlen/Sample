package com.wzf.com.sample.volley;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by soonlen on 2017/3/28 15:16.
 * email wangzheng.fang@zte.com.cn
 */

public class BasicNetwork implements Network {
    @Override
    public NetworkResponse performRequest(Request<?> request) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            connection.setRequestProperty("Charset", "UTF-8");
            if (200 == connection.getResponseCode()) {//连接正常
                String result = getString(connection.getInputStream());
                Log.e("BasicNetwork", result);
                byte[] bytes = getBytes(connection.getInputStream(), connection.getContentLength());
                connection.disconnect();
                return new NetworkResponse(200, bytes, null, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytes(InputStream inputStream, int length) {
        byte[] bytes= new byte[0];
        try {
            bytes = new byte[inputStream.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private String getString(InputStream inputStream) {
        if (inputStream == null)
            return null;
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buff = new byte[8192];
        try {
            while (inputStream.read(buff) != -1) {
                stringBuilder.append(new String(buff, 0, buff.length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
