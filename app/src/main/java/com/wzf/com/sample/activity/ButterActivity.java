package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wzf.com.sample.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by soonlen on 2017/1/19.
 * 框架说明地址：http://jakewharton.github.io/butterknife/
 * 1、强大的View绑定和Click事件处理功能，简化代码，提升开发效率
 * 2、方便的处理Adapter里的ViewHolder绑定问题
 * 3、运行时不会影响APP效率，使用配置方便
 * 4、代码清晰，可读性强
 * 使用：
 * dependencies{
 * compile 'com.jakewharton:butterknife:8.4.0'
 * annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
 * }
 * Butter和Dagger2有冲突，目前已解决,http://blog.csdn.net/norwaya007/article/details/52160143
 */

public class ButterActivity extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView tvName;
    @BindView(R.id.button4)
    Button btnTest;
    @BindView(R.id.toggleButton)
    ToggleButton tbTest;
    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.checkedTextView)
    CheckedTextView checkedTextView;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    private Unbinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter);
        binder = ButterKnife.bind(this);
        tvName.setText("这是测试Butter注入框架使用");
        btnTest.setText("按钮……");
        tbTest.setChecked(true);
        radioButton.toggle();
        checkedTextView.toggle();
        seekBar.setProgress(50);


        HashMap<String, String> map = new HashMap<>();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }
}
