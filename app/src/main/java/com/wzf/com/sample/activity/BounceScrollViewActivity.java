package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.Tools;

/**
 * Created by soonlen on 2016/11/21.
 */

public class BounceScrollViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_scrollview);
    }
    public void clickButton(View v) {
        Tools.showInfo(this, "click button");
    }
}
