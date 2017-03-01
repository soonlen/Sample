package com.wzf.com.sample.vitamio;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wzf.com.sample.R;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by soonlen on 2017/3/1 10:28.
 * email wangzheng.fang@zte.com.cn
 */

public class MyMediaController extends MediaController {

    private GestureDetector mGestureDetector;
    private ImageButton img_back;//返回按钮
    private TextView mFileName;//文件名
    private VideoView videoView;
    private Activity activity;
    private String videoname;//视频名称
    private int controllerWidth = 0;//设置mediaController高度为了使横屏时top显示在屏幕顶端

    private View mVolumeBrightnessLayout;//提示窗口
    private ImageView mOperationBg;//提示图片
    private TextView mOperationTv;//提示文字
    private AudioManager mAudioManager;
    private SeekBar progress;
    private boolean mDragging;
    private MediaPlayerControl player;
    //最大声音
    private int mMaxVolume;
    // 当前声音
    private int mVolume = -1;
    //当前亮度
    private float mBrightness = -1f;
    private ImageView mIvScale;


    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMediaController(Context context) {
        super(context);
    }

    //返回监听
    private View.OnClickListener backListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (activity != null) {
                activity.finish();
            }
        }
    };

    //videoview 用于对视频进行控制的等，activity为了退出
    public MyMediaController(VideoView videoView, Activity activity) {
        super(activity);
        this.videoView = videoView;
        this.activity = activity;
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        controllerWidth = wm.getDefaultDisplay().getWidth();
        mGestureDetector = new GestureDetector(activity, new MyGestureListener());

    }

    @Override
    protected View makeControllerView() {
        //此处的   mymediacontroller  为我们自定义控制器的布局文件名称
        View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(getResources().getIdentifier("mymediacontroller", "layout", getContext().getPackageName()), this);
        v.setMinimumHeight(controllerWidth);
        //获取控件
        img_back = (ImageButton) v.findViewById(getResources().getIdentifier("mediacontroller_top_back", "id", activity.getPackageName()));
        mFileName = (TextView) v.findViewById(getResources().getIdentifier("mediacontroller_filename", "id", activity.getPackageName()));
        //缩放控件
        mIvScale = (ImageView) v.findViewById(getResources().getIdentifier("mediacontroller_scale", "id", activity.getPackageName()));

        if (mFileName != null) {
            mFileName.setText(videoname);
        }
        //声音控制
        mVolumeBrightnessLayout = (RelativeLayout) v.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) v.findViewById(R.id.operation_bg);
        mOperationTv = (TextView) v.findViewById(R.id.operation_tv);
        mOperationTv.setVisibility(View.GONE);
        mAudioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //注册事件监听
        img_back.setOnClickListener(backListener);
        mIvScale.setOnClickListener(scaleListener);
        return v;
    }
    private View.OnClickListener scaleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (activity != null) {
                switch (activity.getResources().getConfiguration().orientation) {
                    case Configuration.ORIENTATION_LANDSCAPE://横屏
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        break;
                    case Configuration.ORIENTATION_PORTRAIT://竖屏
                        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        break;
                }

            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
//            return super.onSingleTapUp(e);
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当手势结束，并且是单击结束时，控制器隐藏/显示
            toggleMediaControlsVisiblity();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
//            return super.onDown(e);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playOrPause();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            float oldX = e1.getX();
            float oldY = e1.getY();
            float y = e2.getRawY();
            Display display = activity.getWindowManager().getDefaultDisplay();
            int screemWidth = display.getWidth();
            int screemHeight = display.getHeight();
            if (oldX > 3.0 / 4 * screemWidth) {//右边加声音
                onVolumSlide((oldY - y) / screemHeight);
            } else if (oldX < 1.0 / 4 * screemWidth) {//左边调节亮度
                onBrightnessSlide((oldY - y) / screemHeight);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    private void onBrightnessSlide(float v) {
        mBrightness = activity.getWindow().getAttributes().screenBrightness;
        if (mBrightness <= 0.00f)
            mBrightness = 0.50f;
        if (mBrightness < 0.01f)
            mBrightness = 0.01f;
        WindowManager.LayoutParams attri = activity.getWindow().getAttributes();
        attri.screenBrightness = mBrightness + v;
        if (attri.screenBrightness > 1.0f)
            attri.screenBrightness = 1.0f;
        if (attri.screenBrightness < 0.01f)
            attri.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(attri);
    }

    private void onVolumSlide(float volumScale) {
        mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.e(this.getClass().getSimpleName(), "current vomume is " + mVolume);
        if (mVolume < 0) {
            mVolume = 0;
        }
        mVolume = (int) (mVolume + volumScale * mMaxVolume);
        if (mVolume > mMaxVolume) {
            mVolume = mMaxVolume;
        }
        if (mVolume < 0) {
            mVolume = 0;
        }
        mOperationTv.setText((int) (((double) mVolume / mMaxVolume) * 100) + "%");
        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolume, 0);
    }

    private void playOrPause() {
        if (videoView != null) {
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
        }
    }

    /**
     * 隐藏或显示
     */
    private void toggleMediaControlsVisiblity() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }
}
