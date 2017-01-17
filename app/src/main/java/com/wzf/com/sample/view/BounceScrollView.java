package com.wzf.com.sample.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.wzf.com.sample.util.L;

/**
 * Created by soonlen on 2016/11/21.
 */

public class BounceScrollView extends ScrollView {

    private final static int MAX_SCROLL = 4;
    private Rect mRect = new Rect();
    private float y;
    private View inner;
    int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private float scrollY;


    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0)
            inner = getChildAt(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scrollY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                if (Math.abs(y - scrollY) > mTouchSlop) {
                    scrollY = y;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {
            handlerTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void handlerTouchEvent(MotionEvent ev) {
//        L.e("motion :" + ev.getAction());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                /**
                 * MAX_SCROLL=4 表示 拖动的距离为屏幕的高度的1/4
                 */
                int deltaY = (int) (preY - nowY) / MAX_SCROLL;
                if (isNeedMove()) {
                    if (mRect.isEmpty()) {
                        mRect.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                    }
                    int yy = inner.getTop() - deltaY;
                    inner.layout(inner.getLeft(), yy, inner.getRight(), inner.getBottom() + yy);
                }
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    resetAnimation();
                }
                break;
        }
    }

    private void resetAnimation() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, inner.getTop(), mRect.top);
        anim.setDuration(200);
        inner.startAnimation(anim);
        inner.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !mRect.isEmpty();
    }

    /**
     * 是否需要滚动
     *
     * @return
     */
    private boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        float scrollY = getScrollY();
        L.e("offset:" + offset + ",scrollY:" + scrollY);
        if (scrollY == 0 || offset == scrollY) {
            return true;
        }
        View lastChild = getChildAt(getChildCount() - 1);
        int bot = lastChild.getBottom();
        if (bot < getHeight()) {
            return true;
        }
        return false;
    }
}
