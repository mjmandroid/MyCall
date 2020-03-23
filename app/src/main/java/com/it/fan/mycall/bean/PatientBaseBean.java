package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/9/4.
 */

public class PatientBaseBean {
    private int result;

    private String msg;

    private PatientListBean data;


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

    public PatientListBean getData() {
        return data;
    }

    public void setData(PatientListBean data) {
        this.data = data;
    }
}
