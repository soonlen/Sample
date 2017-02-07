package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.annotation.AnnotateUtils;
import com.wzf.com.sample.annotation.ContentView;
import com.wzf.com.sample.annotation.OnClick;
import com.wzf.com.sample.annotation.ViewInject;
import com.wzf.com.sample.util.Tools;

//注解测试
@ContentView(value = R.layout.activity_annotation)
public class AnnotationActivity extends AppCompatActivity {

    @ViewInject(R.id.activity_annotation_tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_annotation);
        AnnotateUtils.inject(this);
        tv.setText("注解测试……");
    }

    @OnClick({R.id.activity_annotation_btn_test, R.id.activity_annotation_btn_test2})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.activity_annotation_btn_test:
                Tools.showInfo(this, "注入事件绑定测试……");
                break;
            case R.id.activity_annotation_btn_test2:
                Tools.showInfo(this, "注入事件绑定测试2……");
                break;
        }
    }
}
