package com.wzf.com.sample.eventbus;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ItemListFragment extends ListFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new Event.ItemListEvent(Item.ITEMS));
            }
        }).start();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Event.ItemListEvent event) {
        setListAdapter(new ArrayAdapter<Item>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, event.getItems()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
        EventBus.getDefault().post(getListView().getAdapter().getItem(position));
    }
}
