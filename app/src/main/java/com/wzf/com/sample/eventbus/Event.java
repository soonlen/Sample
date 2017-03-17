package com.wzf.com.sample.eventbus;

import java.util.List;

/**
 * Created by soonlen on 2017/3/8 13:59.
 * email wangzheng.fang@zte.com.cn
 */

public class Event {
    /**
     * 列表加载事件
     */
    public static class ItemListEvent {
        private List<Item> items;

        public ItemListEvent(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }
}
