package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wzf.com.sample.R;
import com.wzf.com.sample.util.L;
import com.wzf.com.sample.volley.RequestQueue;
import com.wzf.com.sample.volley.Response;
import com.wzf.com.sample.volley.StringRequest;
import com.wzf.com.sample.volley.Volley;

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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
        requestQueue.add(request);
    }


}
