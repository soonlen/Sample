package com.wzf.com.sample.json;

import java.util.List;

/**
 * Created by soonlen on 2017/2/16 11:32.
 * email wangzheng.fang@zte.com.cn
 */

public class School {
    private String name;
    private String address;
    List<Student> students;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
