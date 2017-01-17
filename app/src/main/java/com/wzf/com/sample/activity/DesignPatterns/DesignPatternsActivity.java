package com.wzf.com.sample.activity.DesignPatterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.adapter.CommonAdapter;
import com.wzf.com.sample.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soonlen on 2016/12/6.
 */

public class DesignPatternsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mLv;
    private List<String> listPatters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_patterns);
        mLv = (ListView) findViewById(R.id.activity_design_patterns_lv);
        listPatters.add("Singleton");
        CommonAdapter<String> adapter = new CommonAdapter<String>(this, listPatters, R.layout.design_patterns_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.design_patterns_item_tv, item);
            }
        };
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            startActivity(new Intent(this, Class.forName("com.wzf.com.sample.activity.DesignPatterns" + listPatters.get(position))));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
