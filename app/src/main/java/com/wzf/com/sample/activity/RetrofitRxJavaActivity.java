package com.wzf.com.sample.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.retrofit.ReService;
import com.wzf.com.sample.retrofit.RetrofitEntity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by soonlen on 2017/1/22.
 * Retrofit+RxJava使用案例:http://blog.csdn.net/wzgiceman/article/details/52908989
 */

public class RetrofitRxJavaActivity extends AppCompatActivity {

    @BindView(R.id.button7)
    Button btnGet;
    @BindView(R.id.textView4)
    TextView tvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rxjava);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button7)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button7:
                retrofit();
                break;
        }
    }

    private void retrofit() {
        //手动创建一个OkHttpClient并设置超时时间
        okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        final Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://www.izaodao.com/Api/")
                .build();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("正在加载中……");

        ReService service = retrofit.create(ReService.class);
        Observable<RetrofitEntity> observable = service.getAllVedioBy(true);
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<RetrofitEntity>() {
                            @Override
                            public void onCompleted() {
                                if (pd != null && pd.isShowing())
                                    pd.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (pd != null && pd.isShowing())
                                    pd.dismiss();
                                tvShow.setText("获取出错……");
                            }

                            @Override
                            public void onNext(RetrofitEntity retrofitEntity) {
                                tvShow.setText(retrofitEntity.getData().toString());
                            }

                            @Override
                            public void onStart() {
                                super.onStart();
                                pd.show();
                            }
                        }
                );
    }
}
