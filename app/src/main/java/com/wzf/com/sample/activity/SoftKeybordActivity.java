package com.wzf.com.sample.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;
import com.wzf.com.sample.util.SoftKeyboardUtil;
import com.wzf.com.sample.util.Tools;

/**
 * Created by soonlen on 2017/1/5.
 */

public class SoftKeybordActivity extends AppCompatActivity {

    private EditText et;
    private boolean isShow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_keybord);
        et = (EditText) findViewById(R.id.editText);
    }

    public void popup(View v) {
        SoftKeyboardUtil.showSoftInputForced(this, et);
    }

    public void hide(View v) {
        SoftKeyboardUtil.hideSoftInputForced(this, et);
    }

    public void judge(View v) {
        Tools.showInfo(SoftKeybordActivity.this, SoftKeyboardUtil.isSoftShowing(this) ? "弹出" : "隐藏");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取当前屏幕内容的高度
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //获取iew可见区域的bottom
                Rect rect = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                L.e("left:" + left + ",top:" + top + ",right:" + right + ",bottom:" + bottom + ",oldLeft:" + oldLeft + ",oldTop:" + oldTop + ",oldRight:" + oldRight + ",oldBottom:" + oldBottom + ",rect.bottom:" + rect.bottom);
                if (bottom != 0 && oldBottom != 0 && bottom - rect.bottom <= 0) {
                    Tools.showInfo(SoftKeybordActivity.this, "隐藏");
                    isShow = false;
                } else {
                    Tools.showInfo(SoftKeybordActivity.this, "弹出");
                    isShow = true;
                }
            }
        });
    }
}
