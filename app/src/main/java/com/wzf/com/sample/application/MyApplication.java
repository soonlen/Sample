package com.wzf.com.sample.application;

import android.app.Application;
import android.content.Context;

import com.netease.scan.QrScan;
import com.netease.scan.QrScanConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wzf.com.sample.R;

import io.realm.Realm;

/**
 * Created by soonlen on 2017/1/24.
 */

public class MyApplication extends Application {

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        initQrScan();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    protected RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }

    private void initQrScan() {
        // 自定义配置
        QrScanConfiguration configuration = new QrScanConfiguration.Builder(this)
                .setTitleHeight(53)
                .setTitleText("来扫一扫")
                .setTitleTextSize(18)
                .setTitleTextColor(android.R.color.white)
                .setTipText("将二维码放入框内扫描~")
                .setTipTextSize(14)
                .setTipMarginTop(40)
                .setTipTextColor(android.R.color.white)
                .setSlideIcon(R.drawable.capture_add_scanning)
                .setAngleColor(android.R.color.white)
                .setMaskColor(android.R.color.black)
                .setScanFrameRectRate((float) 0.8)
                .build();
        QrScan.getInstance().init(configuration);
    }
}
