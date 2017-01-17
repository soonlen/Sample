package com.wzf.com.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soonlen on 2016/11/24.
 */

public class XListView extends ListView implements AbsListView.OnScrollListener {

    private float lastY = -1;
    private XListViewHeader mHeaderView;
    private XListViewFooter mFooterView;
    private Scroller mScroller; // used for scroll back
    private RelativeLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.

    private boolean mEnablePullLoad;
    private boolean mPullLoading;
    private boolean mIsFooterReady = false;
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    private IXListViewListener listViewListener;

    public XListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithContext(context);
    }

    public XListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        mHeaderView = new XListViewHeader(context);
        super.setOnScrollListener(this);
        mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
        addHeaderView(mHeaderView);

        mFooterView = new XListViewFooter(context);
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderViewHeight = mHeaderViewContent.getHeight();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (!mIsFooterReady) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    public void stopRefresh() {
        if (mPullRefreshing) {
            mPullRefreshing = false;
            resetHeadViewHeight();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mHeaderTimeView.setText(format.format(new Date()));
        }
    }

    public void stopLoading() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(XListViewFooter.STATE_NORMAL);
//            resetFootViewHeight();
        }
    }

    public void enablePullRefresh(boolean enable) {
        mEnablePullRefresh = enable;
        if (mEnablePullRefresh) {
            mHeaderViewContent.setVisibility(VISIBLE);
        } else {
            mHeaderViewContent.setVisibility(INVISIBLE);
        }
    }

    public void enablePullLoad(boolean enable) {
        mEnablePullLoad = enable;
        if (mEnablePullLoad) {
            setFooterDividersEnabled(true);
            mFooterView.show();
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        } else {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            setFooterDividersEnabled(false);
        }
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        if (listViewListener != null) {
            listViewListener.onLoadMore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (lastY == -1) {
            lastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffY = ev.getRawY() - lastY;
                float delta = diffY / OFFSET_RADIO;
                lastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || diffY > 0)) {//滚动到顶端
//                    L.e("diffY:" + diffY);
                    updateHeadViewHeight(delta);
                } else if (getLastVisiblePosition() == getCount() - 1
                        && mFooterView.getBottomMargin() >= 0
                        && (diffY < 0)) {//滚动到底部
                    L.e("diffY:" + diffY);
                    updateFooterViewHeight(-delta);
                }
                break;
            default:
                lastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        mPullRefreshing = true;
                        mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
                        if (listViewListener != null) {
                            listViewListener.onRefresh();
                        }
                    }
                    resetHeadViewHeight();
                } else if (getLastVisiblePosition() == getCount() - 1) {
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA && !mPullLoading) {
                        startLoadMore();
                    }
                    resetFootViewHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void resetFootViewHeight() {
        int botMargin = mFooterView.getBottomMargin();
        if (botMargin > 0) {
            mScroller.startScroll(0, botMargin, 0, -botMargin, 200);
            mScrollBack = SCROLLBACK_FOOTER;
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XListViewFooter.STATE_LOADING);
        if (listViewListener != null) {
            listViewListener.onLoadMore();
        }
    }

    private void updateFooterViewHeight(float delta) {
        int height = (int) (delta + mFooterView.getBottomMargin());
        L.e("foot height:" + height);
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                mFooterView.setState(XListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(XListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    /**
     * 复位头部view
     */
    private void resetHeadViewHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0)
            return;
        if (height <= mHeaderViewHeight && mPullRefreshing) {
            return;
        }
        int finalHeight = 0;
        if (mPullRefreshing && height > mHeaderViewHeight) {
            L.e("start scroll...");
            finalHeight = mHeaderViewHeight;
        }
        mScroller.startScroll(0, height, 0, finalHeight - height, 300);
        mScrollBack = SCROLLBACK_HEADER;
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            L.e("computeScroll...");
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    private void updateHeadViewHeight(float delta) {
        mHeaderView.setVisiableHeight((int) (delta + mHeaderView.getVisiableHeight()));
        if (!mPullRefreshing && mEnablePullLoad) {
            if (mHeaderView.getVisiableHeight() < mHeaderViewHeight) {
                mHeaderView.setState(XListViewHeader.STATE_NORMAL);
            } else {
                mHeaderView.setState(XListViewHeader.STATE_READY);
            }
        }
        setSelection(0);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public IXListViewListener getListViewListener() {
        return listViewListener;
    }

    public void setListViewListener(IXListViewListener listViewListener) {
        this.listViewListener = listViewListener;
    }

    public interface IXListViewListener {
        void onRefresh();

        void onLoadMore();
    }
}
