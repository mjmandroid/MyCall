package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/6/7.
 */

public class QueryTypeBean {

    private String  num;
    private String type;

    public QueryTypeBean(String num, String type) {
        this.num = num;
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
