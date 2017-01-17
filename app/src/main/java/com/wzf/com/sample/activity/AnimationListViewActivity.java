package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by soonlen on 2017/1/4.
 */

public class AnimationListViewActivity extends AppCompatActivity {

    private ListView listView;
    private CommonAdapter<String> adapter;
    List<String> strs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_listview);
        listView = (ListView) findViewById(R.id.activity_animation_listview_lv);
        strs = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            strs.add("这是一个子项" + (i + 1));
        }
        /*adapter = new CommonAdapter<String>(this, strs, R.layout.simple_list_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.simple_list_item_tv, item);
            }
        };
        listView.setAdapter(adapter);*/
        MyAdatper adatper = new MyAdatper();
        listView.setAdapter(adatper);
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.trip_fragemnt_anim);
//        listView.setAnimation(anim);
    }


    class MyAdatper extends BaseAdapter {

        Animation animation;
        private Map<Integer, Boolean> isFrist;


        public MyAdatper() {
            animation = AnimationUtils.loadAnimation(AnimationListViewActivity.this, R.anim.woniu_list_item);
            isFrist = new HashMap<>();
        }

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int position) {
            return strs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(AnimationListViewActivity.this).inflate(R.layout.simple_list_item, null);
                holder = new Holder();
                holder.tv = (TextView) convertView.findViewById(R.id.simple_list_item_tv);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.tv.setText(strs.get(position));

            if (!isFrist.containsKey(position)) {
                convertView.startAnimation(animation);
                isFrist.put(position, true);
            }
            return convertView;
        }

        class Holder {
            TextView tv;
        }
    }
}
