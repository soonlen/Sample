package com.wzf.com.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wzf.com.sample.R;
import com.wzf.com.sample.download.DownloadInfo;
import com.wzf.com.sample.download.DownloadManager;
import com.wzf.com.sample.util.L;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.download_progress)
    TextView tvProgress;

    private final static String URL = "http://120.198.244.55:9999/dlc2.pconline.com.cn/filedown_78212_8080199/MXr52WEf/pconline1484571141047.apk";
//    private final static String URL = "http://gdown.baidu.com/data/wisegame/fc328fa3a33efe57/QQ_482.apk";

    private DownloadHandler handler = new DownloadHandler(this);
    private DownloadInfo info;

    class DownloadHandler extends Handler {

        private WeakReference<Activity> ref;

        public DownloadHandler(Activity activity) {
            ref = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DownloadActivity activity = (DownloadActivity) ref.get();
            if (activity != null) {
                activity.handleMsg(msg);
            }
        }
    }

    public void handleMsg(Message msg) {
        switch (msg.what) {
            case 11://更新进度条
                int progress = msg.arg1;
                mProgressBar.setProgress(progress);
                tvProgress.setText(progress + "%");
                break;
            case 12://下载完成
                DownloadInfo info2 = (DownloadInfo) msg.obj;
                L.e("当前下载的总大小：" + info2.getStartPos());
                Toast.makeText(this, "下载完成", Toast.LENGTH_SHORT).show();
                break;
            case 13:
                Toast.makeText(this, "下载暂停", Toast.LENGTH_SHORT).show();
                break;
            case 14:
                Toast.makeText(this, "下载取消", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        ButterKnife.bind(this);
        info = new DownloadInfo();
        info.setUrl(URL);
    }


    @OnClick({R.id.pause, R.id.start})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pause:
                DownloadManager.getInstance().pause(URL);
                L.e("暂停了");
                break;
            case R.id.start:
                DownloadManager.getInstance().download(this, handler, URL);
                L.e("开始下载了");
                break;
        }
    }
}
