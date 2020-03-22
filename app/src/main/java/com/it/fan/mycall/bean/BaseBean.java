package com.it.fan.mycall.bean;

/**
 * Description:$｛Method｝
 * Data: 2018/7/6 17:41
 *
 * @author: fan
 */
public class BaseBean<T> {
    private int result;

    private String msg;

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
