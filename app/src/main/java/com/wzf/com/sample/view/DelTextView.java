package com.wzf.com.sample.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;


/**
 * Created by soonlen on 2016/6/14.
 */
public class DelTextView extends TextView {

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    private DelTextViewInterface delTextViewInterface;

    public DelTextView(Context context) {
        super(context);
        init();
    }

    public DelTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DelTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//        	throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.btn_delete);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], mClearDrawable, getCompoundDrawables()[3]);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.e("action:" + event.getAction());
        if(event.getAction()==MotionEvent.ACTION_DOWN)
            return true;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                    if (delTextViewInterface != null)
                        delTextViewInterface.del();
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public DelTextViewInterface getDelTextViewInterface() {
        return delTextViewInterface;
    }

    public void setDelTextViewInterface(DelTextViewInterface delTextViewInterface) {
        this.delTextViewInterface = delTextViewInterface;
    }

    public interface DelTextViewInterface {
        void del();
    }
}
