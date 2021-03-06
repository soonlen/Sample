package com.wzf.com.sample.volley.my;

/**
 * Created by soonlen on 2017/3/29 15:04.
 * email wangzheng.fang@zte.com.cn
 */

public class LoginResponse {

    /**
     * resultcode : 101
     * reason : 错误的请求KEY!
     * result : null
     * error_code : 10001
     */

    private String resultcode;
    private String reason;
    private Object result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
