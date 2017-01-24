package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by soonlen on 2017/1/20.
 * dagger2框架使用
 *
 */

public class Dagger2TestActivity extends AppCompatActivity {
  /*  @Inject
    Person person;
    @Inject
    Person person2;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 构造桥梁对象
      /*  MainComponent component = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        //注入
        component.inject(this);
        Log.e("dagger", "p1:"+person.toString()+",p2:"+person2.toString());*/
    }
}
