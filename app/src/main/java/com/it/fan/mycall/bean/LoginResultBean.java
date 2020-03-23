package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/6/4.
 */

public class LoginResultBean {

    private String attacheTrue;

    private String attacheVitrual;

    private int id;

    private String userName;

    private String hujingUid;

    private String ding_url;


    public String getHujingUid() {
        return hujingUid;
    }

    public void setHujingUid(String hujingUid) {
        this.hujingUid = hujingUid;
    }

    public String getDing_url() {
        return ding_url;
    }

    public void setDing_url(String ding_url) {
        this.ding_url = ding_url;
    }

    public String getAttacheTrue() {
        return attacheTrue;
    }

    public void setAttacheTrue(String attacheTrue) {
        this.attacheTrue = attacheTrue;
    }

    public String getAttacheVitrual() {
        return attacheVitrual;
    }

    public void setAttacheVitrual(String attacheVitrual) {
        this.attacheVitrual = attacheVitrual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
