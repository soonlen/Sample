package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.recyclerview.DividerGridItemDecoration;
import com.wzf.com.sample.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by soonlen on 2017/2/24 15:27.
 * email wangzheng.fang@zte.com.cn
 */

public class RecycleViewActivity extends AppCompatActivity {

    @BindView(R.id.activity_recycle_view)
    RecyclerView mRecyclerView;
    private List<String> datas;
    private ReAdapter mAdater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        initData();
        ButterKnife.bind(this);
        initView();
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            datas.add("第" + (i + 1));
        }
    }

    private void initView() {
        mAdater = new ReAdapter();
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setAdapter(mAdater);
//        LruCache
//        DiskLruCache
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycle_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        L.e("======================");
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    class ReAdapter extends RecyclerView.Adapter<ReAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(RecycleViewActivity.this).inflate(R.layout.recycle_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.tv.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private TextView tv;

            public MyHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.recycle_item_tv);
            }
        }
    }
}
