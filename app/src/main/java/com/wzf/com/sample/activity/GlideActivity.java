package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wzf.com.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by soonlen on 2017/1/23.
 */

public class GlideActivity extends AppCompatActivity {

    @BindView(R.id.button8)
    Button btnGet;
    @BindView(R.id.imageView)
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button8)
    public void onClick(View v) {
        String url = "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2F7aec54e736d12f2ee289bffe4cc2d5628435689b.jpg&thumburl=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2F7aec54e736d12f2ee289bffe4cc2d5628435689b.jpg";
        Glide.with(this)
                .load(url)
                .into(iv);
    }

}
