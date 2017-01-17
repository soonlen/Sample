package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzf.com.sample.R;
import com.wzf.com.sample.view.ToastWithPic;

/**
 * Created by soonlen on 2016/11/29.
 */

public class UtilActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_util);
    }

    public void toast(View v) {
        ToastWithPic.ToastWithPic(this, R.mipmap.ic_launcher, 1000);
    }
}
