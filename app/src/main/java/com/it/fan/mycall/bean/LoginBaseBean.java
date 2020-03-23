package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/9/12.
 */

public class LoginBaseBean {
    private int result;

    private String msg;

    private LoginResultBean data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginResultBean getData() {
        return data;
    }

    public void setData(LoginResultBean data) {
        this.data = data;
    }
}
