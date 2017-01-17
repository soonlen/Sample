package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.view.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soonlen on 2016/11/24.
 */

public class XListViewActivity extends AppCompatActivity
        implements XListView.IXListViewListener {

    private XListView xListView;
    private List<String> datas = new ArrayList<String>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlistview);
        initData();
        xListView = (XListView) findViewById(R.id.activity_xlistview_lv);
        myAdapter = new MyAdapter();
        xListView.setAdapter(myAdapter);
//        xListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.xlistview_header, null));
//        xListView.addFooterView(LayoutInflater.from(this).inflate(R.layout.xlist_view_footer, null));
        xListView.setListViewListener(this);
        xListView.enablePullLoad(true);
    }

    private void initData() {
        if (datas.size() > 0)
            datas.clear();
        for (int i = 0; i < 12; i++) {
            datas.add("数据" + (i + 1));
        }
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }

        }, 2000);
    }

    private void refreshData() {
        initData();
        myAdapter.notifyDataSetChanged();
        xListView.stopRefresh();
    }

    private void addData() {
        datas.add("这是一个新的数据");
        myAdapter.notifyDataSetChanged();
        xListView.stopLoading();
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                addData();
            }
        }, 2000);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(XListViewActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                holder = new MyHolder();
                holder.tv = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }
            holder.tv.setText(datas.get(position));
            return convertView;
        }

        class MyHolder {
            TextView tv;
        }
    }
}
