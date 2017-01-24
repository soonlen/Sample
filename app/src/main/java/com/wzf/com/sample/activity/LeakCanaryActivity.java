package com.wzf.com.sample.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.squareup.leakcanary.RefWatcher;
import com.wzf.com.sample.R;
import com.wzf.com.sample.application.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by soonlen on 2017/1/24.
 */

public class LeakCanaryActivity extends AppCompatActivity {

    @BindView(R.id.btn_leak)
    Button btnLeak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);
        ButterKnife.bind(this);
        RefWatcher watcher = MyApplication.getRefWatcher(this);
        watcher.watch(this);
    }

    @OnClick(R.id.btn_leak)
    public void onClick(View v) {
        startAsyncTask();
        finish();
    }

    void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(20000);
                return null;
            }
        }.execute();
    }
}
