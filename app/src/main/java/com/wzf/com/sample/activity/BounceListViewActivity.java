package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.wzf.com.sample.R;
import com.wzf.com.sample.view.BounceListView;

/**
 * Created by soonlen on 2016/11/21.
 */

public class BounceListViewActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_listview);
        BounceListView bounceListView = (BounceListView) findViewById(R.id.bounce_lv);
        String[] strs = new String[]{"张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三","张三"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        bounceListView.setAdapter(adapter);
    }
}
