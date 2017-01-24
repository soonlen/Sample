package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by soonlen on 2017/1/22.
 * retrofit使用案例,http://blog.csdn.net/duanyy1990/article/details/52139294
 */

public class RetrofitActivity extends AppCompatActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.button6)
    Button btnLoc;
    @BindView(R.id.tv_loc)
    TextView tvLoc;

    String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
    String phoneNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        etPhone.setText("15258899016");

    }

    @OnClick(R.id.button6)
    public void onClick(View v) {
        phoneNum = etPhone.getText().toString().trim();
        requestLoc();
    }

    private void requestLoc() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tcc.taobao.com")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RequestSerives requestSerives = retrofit.create(RequestSerives.class);//这里采用的是Java的动态代理模式
        Call<String> call = requestSerives.getString(phoneNum);//传入我们请求的键值对的值
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                L.e("=======" + response.body().toString());
                tvLoc.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                L.e("------" + t);
                tvLoc.setText("获取失败……");
            }
        });
    }


    interface RequestSerives {
        @GET("cc/json/mobile_tel_segment.htm")
        Call<String> getString(@Query("tel") String tel);
    }
}
