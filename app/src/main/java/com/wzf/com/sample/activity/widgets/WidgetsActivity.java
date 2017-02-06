package com.wzf.com.sample.activity.widgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.adapter.CommonAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soonlen on 2017/2/6.
 */

public class WidgetsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.activity_widgets_lv)
    ListView listView;

    private CommonAdapter<String> adapter;
    String[] strs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets);
        ButterKnife.bind(this);
        strs = new String[]{"infinite viewpager"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position) {
            case 0://无限viewpager
                intent = new Intent(WidgetsActivity.this, InfiniteViewActivity.class);
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}
