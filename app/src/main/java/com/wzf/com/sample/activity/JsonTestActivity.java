package com.wzf.com.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.wzf.com.sample.R;
import com.wzf.com.sample.json.School;
import com.wzf.com.sample.json.Student;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JsonTestActivity extends AppCompatActivity {

    private School school;
    @BindView(R.id.activity_json_test_tv_gson)
    TextView tvGson;
    @BindView(R.id.activity_json_test_tv_jackson)
    TextView tvJackson;

    private final static int CYCLE_COUNT = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_json_test_btn_gson, R.id.activity_json_test_btn_jackson})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.activity_json_test_btn_gson:
                gsonParser();
                break;
            case R.id.activity_json_test_btn_jackson:
                jacksonParser();
                break;
        }
    }

    private void jacksonParser() {
        if (school == null) {
            initSchoolData();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        School resultSchool;
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < CYCLE_COUNT; i++) {
            //将对象转化为Json字符串
            try {
                jsonString = objectMapper.writeValueAsString(school);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //将json字符串解析成java对象
            try {
                resultSchool = objectMapper.readValue(jsonString, School.class);
            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        tvJackson.setText("当前循环耗时:" + (System.currentTimeMillis() - startTime) + "ms");
    }


    private void gsonParser() {
        if (school == null) {
            initSchoolData();
        }
        Gson gson = new Gson();
        String jsonString;
        School resultSchool;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < CYCLE_COUNT; i++) {
            //将对象转化为json字符串
            jsonString = gson.toJson(school).toString();
            //将字符串转化为JSON对象
            resultSchool = gson.fromJson(jsonString, School.class);
        }
        tvGson.setText("当前循环耗时:" + (System.currentTimeMillis() - startTime) + "ms");
    }

    private School initSchoolData() {
        school = new School();
        school.setAddress("Nanjing");
        school.setName("NUPT");
        List<Student> list = new ArrayList<Student>();
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            student.setAge(20 + i);
            student.setId("1000" + i);
            student.setName("stu" + 1);
            list.add(student);
        }
        school.setStudents(list);
        return school;
    }
}
