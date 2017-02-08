package com.wzf.com.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrScanActivity extends AppCompatActivity {

    @BindView(R.id.activity_qr_scan_btn)
    Button btn;
    @BindView(R.id.activity_qr_scan_tv)
    TextView tv;


    private CaptureActivity mCaptureContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_qr_scan_btn)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_qr_scan_btn:
                cap();
                break;
        }
    }

    private void cap() {
        QrScan.getInstance().launchScan(this, new IScanModuleCallBack() {
            @Override
            public void OnReceiveDecodeResult(final Context context, String result) {
                mCaptureContext = (CaptureActivity) context;
                tv.setText(result);
                AlertDialog dialog = new AlertDialog.Builder(mCaptureContext)
                        .setMessage(result)
                        .setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                QrScan.getInstance().restartScan(mCaptureContext);
                            }
                        })
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                QrScan.getInstance().finishScan(mCaptureContext);
                            }
                        })
                        .create();
                dialog.show();
            }
        });

    }
}
