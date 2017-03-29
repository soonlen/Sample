package com.wzf.com.sample.volley.my;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by soonlen on 2017/3/29 14:12.
 * email wangzheng.fang@zte.com.cn
 */

public class JSONHttpService implements IHttpService {

    private IHttpListener iHttpListener;
    private HttpClient httpClient = new DefaultHttpClient();
    private String mUrl;
    private byte[] mRequestData;
    private HttpRequestBase httpRequestBase;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void excute() {
        httpRequestBase = new HttpPost(mUrl);
        if (mRequestData != null) {
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(mRequestData);
            ((HttpPost) httpRequestBase).setEntity(byteArrayEntity);
        }
        try {
            this.httpClient.execute(httpRequestBase, new HttpResponseHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHttpCallback(IHttpListener listener) {
        this.iHttpListener = listener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.mRequestData = requestData;
    }

    /**
     * http响应处理
     */
    private class HttpResponseHandler extends BasicResponseHandler {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 == statusCode) {
                HttpEntity responseEntity = response.getEntity();
                if (iHttpListener != null) {
                    try {
                        InputStream inputstream = responseEntity.getContent();
                        iHttpListener.onSuccess(inputstream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (iHttpListener != null) {
                    iHttpListener.onError();
                }
            }
            return null;
        }
    }
}
