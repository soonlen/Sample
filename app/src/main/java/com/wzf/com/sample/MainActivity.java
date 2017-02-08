package com.wzf.com.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wzf.com.sample.activity.AnimationListViewActivity;
import com.wzf.com.sample.activity.AnnotationActivity;
import com.wzf.com.sample.activity.BounceListViewActivity;
import com.wzf.com.sample.activity.BounceScrollViewActivity;
import com.wzf.com.sample.activity.ButterActivity;
import com.wzf.com.sample.activity.CommAdapterActivity;
import com.wzf.com.sample.activity.Dagger2TestActivity;
import com.wzf.com.sample.activity.DataBindTestActivity;
import com.wzf.com.sample.activity.DesignPatterns.DesignPatternsActivity;
import com.wzf.com.sample.activity.GlideActivity;
import com.wzf.com.sample.activity.LeakCanaryActivity;
import com.wzf.com.sample.activity.RealmTestActivity;
import com.wzf.com.sample.activity.RetrofitActivity;
import com.wzf.com.sample.activity.RetrofitRxJavaActivity;
import com.wzf.com.sample.activity.ScreemShotActivity;
import com.wzf.com.sample.activity.SoftKeybordActivity;
import com.wzf.com.sample.activity.TextActivity;
import com.wzf.com.sample.activity.UtilActivity;
import com.wzf.com.sample.activity.WheelViewActivity;
import com.wzf.com.sample.activity.XListViewActivity;
import com.wzf.com.sample.activity.widgets.WidgetsActivity;
import com.wzf.com.sample.loadingview.LoadingViewActivity;
import com.wzf.com.sample.webview.WebViewActivity;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private ListView lv;
    String[] strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        strs = new String[]{
                "Loading",
                "WebViewActivity",
                "BounceListView",
                "BounceScrollView",
                "XListView",
                "ClearEditText",
                "UtilActivity",
                "WheelView",
                "CommAdapter",
                "DesignPatter",
                "AnimationListView",
                "SoftKeybordActivity",
                "ButterActivity",
                "ScreemShotActivity",
                "DataBindTestActivity",
                "Dagger2TestActivity",
                "RetrofitActivity",
                "RetrofitRxJavaActivity",
                "GlideActivity",
                "LeakCanaryActivity",
                "RealmTestActivity",
                "WidgetsActivity",
                "AnnotationActivity",
                "QrScanActivity"
        };
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.main_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        lv.setAdapter(adapter);
    }

    private void initListener() {
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(MainActivity.this, LoadingViewActivity.class));
                break;
            case 1:
                Intent inWeb = new Intent(MainActivity.this, WebViewActivity.class);
                inWeb.putExtra("url", "http://www.baidu.com");
                inWeb.putExtra("back", true);
                startActivity(inWeb);
                break;
            case 2:
                startActivity(new Intent(MainActivity.this, BounceListViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, BounceScrollViewActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, XListViewActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, TextActivity.class));
                break;
            case 6:
                startActivity(new Intent(MainActivity.this, UtilActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, WheelViewActivity.class));
                break;
            case 8:
                startActivity(new Intent(MainActivity.this, CommAdapterActivity.class));
                break;
            case 9:
                startActivity(new Intent(MainActivity.this, DesignPatternsActivity.class));
                break;
            case 10:
                startActivity(new Intent(MainActivity.this, AnimationListViewActivity.class));
                break;
            case 11:
                startActivity(new Intent(MainActivity.this, SoftKeybordActivity.class));
                break;
            case 12:
                startActivity(new Intent(MainActivity.this, ButterActivity.class));
                break;
            case 13:
                startActivity(new Intent(MainActivity.this, ScreemShotActivity.class));
                break;
            case 14:
                startActivity(new Intent(MainActivity.this, DataBindTestActivity.class));
                break;
            case 15:
                startActivity(new Intent(MainActivity.this, Dagger2TestActivity.class));
                break;
            case 16:
                startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
                break;
            case 17:
                startActivity(new Intent(MainActivity.this, RetrofitRxJavaActivity.class));
                break;
            case 18:
                startActivity(new Intent(MainActivity.this, GlideActivity.class));
                break;
            case 19:
                startActivity(new Intent(MainActivity.this, LeakCanaryActivity.class));
                break;
            case 20:
                startActivity(new Intent(MainActivity.this, RealmTestActivity.class));
                break;
            case 21:
                startActivity(new Intent(MainActivity.this, WidgetsActivity.class));
                break;
            case 22:
                startActivity(new Intent(MainActivity.this, AnnotationActivity.class));
                break;
            case 23:
                startActivity(new Intent(MainActivity.this, QrScanActivity.class));
                break;

        }
    }
}
