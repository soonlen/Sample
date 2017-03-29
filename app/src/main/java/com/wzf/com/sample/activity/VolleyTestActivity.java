package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;
import com.wzf.com.sample.volley.User;
import com.wzf.com.sample.volley.my.IDataListener;
import com.wzf.com.sample.volley.my.LoginResponse;
import com.wzf.com.sample.volley.my.volley;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolleyTestActivity extends AppCompatActivity {

    @BindView(R.id.volley_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        ButterKnife.bind(this);
        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest("http://www.baidu.com", new Response.Listener<String>() {

            @Override
            public void response(String response) {
                L.e("success " + response);
                textView.setText(response);
            }
        }, new Response.ErrorListener<String>() {
            @Override
            public void onErrorResponse(String error) {
                L.e("error " + error);
                textView.setText("获取出错了……");
            }
        });
        requestQueue.add(request);*/

    }

    public void volleyRequest(View view) {
        User user = new User("lisi", "123456");
        for (int i =0; i< 50;i  ++) {
            volley.sendRequest(user, "http://apis.juhe.cn/mobile/get",
                    LoginResponse.class, new IDataListener<LoginResponse>() {
                        @Override
                        public void onSuccess(LoginResponse loginResponse) {
                            textView.setText(loginResponse.toString());
                            L.e("thread name " + Thread.currentThread().getName() + " response" + loginResponse.toString());
                        }

                        @Override
                        public void onError() {
                            Toast.makeText(VolleyTestActivity.this, "网络请求出错", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}
