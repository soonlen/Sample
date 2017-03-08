package com.wzf.com.sample.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzf.com.sample.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by soonlen on 2017/3/8 14:04.
 * email wangzheng.fang@zte.com.cn
 */

public class ItemDetailFragment extends Fragment {
    private TextView tvItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, null, false);
        tvItem = (TextView) view.findViewById(R.id.fragment_item_detail_tv);
        return view;
    }

    /**
     * List点击时会发送些事件，接收到事件后更新详情
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Item item) {
        if (item != null)
            tvItem.setText(item.content);
    }
}
