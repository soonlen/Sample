package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.adapter.CommonAdapter;
import com.wzf.com.sample.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soonlen on 2016/12/5.
 */

public class CommAdapterActivity extends AppCompatActivity {

    private ListView mLv;
    private CommonAdapter<String> adapter;
    private List<String> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_adapter);
        mLv = (ListView) findViewById(R.id.activity_comm_adapter_lv);
        datas = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            datas.add("第" + i + "个数据");
        }
        adapter = new CommonAdapter<String>(this, datas, R.layout.comm_adapter_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.comm_adapter_item_tv, item);
            }
        };
        mLv.setAdapter(adapter);
    }
}
