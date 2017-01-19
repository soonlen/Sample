package com.wzf.com.sample.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wzf.com.sample.R;
import com.wzf.com.sample.entity.User;

/**
 * Created by soonlen on 2017/1/19.
 * https://github.com/LyndonChin/MasteringAndroidDataBinding
 */

public class DataBindTestActivity extends AppCompatActivity {
    com.wzf.com.sample.activity.DataBindTestBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        binding = DataBindingUtil.setContentView(this, R.layout.data_bind_test);
        User user = new User("王", "政");
        binding.setUser(user);
    }
}
