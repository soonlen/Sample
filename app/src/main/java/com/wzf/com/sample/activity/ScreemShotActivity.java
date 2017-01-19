package com.wzf.com.sample.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.com.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by soonlen on 2017/1/19.
 */

public class ScreemShotActivity extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.imageview)
    ImageView image;
    @OnClick(R.id.button5)
    public void screemShot() {
        View viewRoot = getWindow().getDecorView().getRootView();
        viewRoot.setDrawingCacheEnabled(true);
        Bitmap screenShotAsBitmap = Bitmap.createBitmap(viewRoot.getDrawingCache());
        viewRoot.setDrawingCacheEnabled(false);
        image.setImageBitmap(screenShotAsBitmap);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screem_shot);
        ButterKnife.bind(this);
    }

}
