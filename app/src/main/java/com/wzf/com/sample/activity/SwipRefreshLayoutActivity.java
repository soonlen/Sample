package com.wzf.com.sample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipRefreshLayoutActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout refreshLayout;
    RecyclerView mRecyclerView;

    private List<String> mDatas;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip_refresh_layout);
        initData();
        initView();
        initLoadmore();
    }

    private boolean isLoading = false;

    private void initLoadmore() {
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if (isLoading)
                        return;
                    isLoading = true;
                    L.e("------------------------------");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            L.e("+++++++++++++++++++++++++++++++++");
                            List<String> footerDatas = new ArrayList<String>();
                            for (int i = 0; i < 10; i++) {
                                footerDatas.add("footer  item" + i);
                            }
                            adapter.AddFooterItem(footerDatas);
                            Toast.makeText(SwipRefreshLayoutActivity.this, "更新了 " + footerDatas.size() + " 条目数据", Toast.LENGTH_SHORT).show();
                            isLoading = false;
                        }
                    }, 3000);

                }
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("第" + (i + 1) + "个数据");
        }
    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_swip_refresh_layout_sr);
        refreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_swip_refresh_layout_rc);
        adapter = new MyAdapter();
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDatas != null)
                    mDatas.clear();
                for (int i = 0; i < 20; i++) {
                    mDatas.add("刷新" + (i + 1));
                }
                adapter.notifyDataSetChanged();
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                Toast.makeText(SwipRefreshLayoutActivity.this, "更新了 " + mDatas.size() + " 条目数据", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }


    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        //上拉加载更多
        public static final int PULLUP_LOAD_MORE = 0;
        //正在加载中
        public static final int LOADING_MORE = 1;
        //没有加载更多 隐藏
        public static final int NO_LOAD_MORE = 2;

        //上拉加载更多状态-默认为0
        private int mLoadMoreStatus = 0;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View itemView = LayoutInflater.from(SwipRefreshLayoutActivity.this).inflate(R.layout.swip_refresh_layout_list_item, parent, false);
                return new SViewHolder(itemView);
            } else if (viewType == TYPE_FOOTER) {
                View itemView = LayoutInflater.from(SwipRefreshLayoutActivity.this).inflate(R.layout.load_more, parent, false);
                return new FooterViewHolder(itemView);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof SViewHolder) {
                ((MyAdapter.SViewHolder) holder).textView.setText(mDatas.get(position));
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                switch (mLoadMoreStatus) {
                    case PULLUP_LOAD_MORE:
                        footerViewHolder.mTvLoadText.setText("上拉加载更多...");
                        break;
                    case LOADING_MORE:
                        footerViewHolder.mTvLoadText.setText("正加载更多...");
                        break;
                    case NO_LOAD_MORE:
                        //隐藏加载更多
                        footerViewHolder.mLoadLayout.setVisibility(View.GONE);
                        break;

                }
            }
        }


        @Override
        public int getItemViewType(int position) {

            if (position + 1 == getItemCount()) {
                //最后一个item设置为footerView
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size() + 1;
        }

        public void AddHeaderItem(List<String> items) {
            mDatas.addAll(0, items);
            notifyDataSetChanged();
        }

        public void AddFooterItem(List<String> items) {
            mDatas.addAll(items);
            notifyDataSetChanged();
        }

        class SViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_tv)
            TextView textView;

            public SViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.pbLoad)
            ProgressBar mPbLoad;
            @BindView(R.id.tvLoadText)
            TextView mTvLoadText;
            @BindView(R.id.loadLayout)
            LinearLayout mLoadLayout;

            public FooterViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
