package com.wzf.com.sample.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wzf.com.sample.R;

public class WebViewActivity extends Activity {
    private WebView webView;
    private boolean enableBackView;
    private View vBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(false);

        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        // 默认使用缓存
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDatabaseEnabled(true);
        // 设置数据库缓存路径
        String appCaceDir = getDir("cache", Context.MODE_WORLD_WRITEABLE).getPath();
        settings.setDatabasePath(appCaceDir);
        //设置 Application Caches 缓存目录
        settings.setAppCachePath(appCaceDir);

        String url = getIntent().getStringExtra("url");
        enableBackView = getIntent().getBooleanExtra("back", false);
        if (enableBackView) {
            vBack = findViewById(R.id.activity_webview_v_back);
            vBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView.canGoBack())
                        webView.goBack();
                    else
                        finish();
                }
            });
        }
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            http://wpa.qq.com/msgrd?v=3&uin=2719840659&site=qq&menu=yes //点击QQ图标的时候
//            tencent://message/?uin=2719840659&Site=qq&Menu=yes
            if (url.startsWith("tel:")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    view.loadUrl(url);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    /**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
