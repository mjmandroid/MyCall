package com.it.fan.mycall.bean;

import java.io.Serializable;

public class VirtualPhoneBean implements Serializable{

    private int attacheId;
    private String attacheTrue;
    private String attacheVitrual;
    private String audioCaller;
    private String bindId;
    private int configId;
    private String configName;
    private long createDate;
    private String deleteFlag;
    private String dingUid;
    private long expireDate;
    private String hujingUid;
    private String status;
    private String type;
    private String userName;
    private String proId;
    private String proName;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getAttacheId() {
        return attacheId;
    }

    public void setAttacheId(int attacheId) {
        this.attacheId = attacheId;
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

    public String getAudioCaller() {
        return audioCaller;
    }

    public void setAudioCaller(String audioCaller) {
        this.audioCaller = audioCaller;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDingUid() {
        return dingUid;
    }

    public void setDingUid(String dingUid) {
        this.dingUid = dingUid;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }

    public String getHujingUid() {
        return hujingUid;
    }

    public void setHujingUid(String hujingUid) {
        this.hujingUid = hujingUid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
