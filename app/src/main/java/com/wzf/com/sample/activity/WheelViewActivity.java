package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wzf.com.sample.R;
import com.wzf.com.sample.view.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soonlen on 2016/11/29.
 */

public class WheelViewActivity extends AppCompatActivity {

    private WheelView vLeft, vCenter, vRight;
    private List<String> strLeft, strCenter, strRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_view);
        vLeft = (WheelView) findViewById(R.id.wheelview_l);
        vCenter = (WheelView) findViewById(R.id.wheelview_c);
        vRight = (WheelView) findViewById(R.id.wheelview_r);
        strLeft = new ArrayList<>();
        strCenter = new ArrayList<>();
        strRight = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            strLeft.add("左第" + (i + 1) + "个");
            strCenter.add("中间" + (i + 1) + "个");
            strRight.add("右边" + (i + 1) + "个");
        }
        vLeft.setData((ArrayList<String>) strLeft);
        vCenter.setData((ArrayList<String>) strCenter);
        vRight.setData((ArrayList<String>) strRight);
    }
}
