package com.it.fan.mycall.bean;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

public class ContactBean extends BaseIndexPinyinBean {

    private int id;
    private int configId;
    private long createTime;
    private int deleteFlag;
    private String userDoctor;
    private String userHospital;
    private String userLabel;
    private String userName;
    private String userPhone;
    private String userPrefix;
    private String userRemark;
    private String vitrualPhone;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUserDoctor() {
        return userDoctor;
    }

    public void setUserDoctor(String userDoctor) {
        this.userDoctor = userDoctor;
    }

    public String getUserHospital() {
        return userHospital;
    }

    public void setUserHospital(String userHospital) {
        this.userHospital = userHospital;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPrefix() {
        return userPrefix;
    }

    public void setUserPrefix(String userPrefix) {
        this.userPrefix = userPrefix;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    public String getVitrualPhone() {
        return vitrualPhone;
    }

    public void setVitrualPhone(String vitrualPhone) {
        this.vitrualPhone = vitrualPhone;
    }

    @Override
    public String getTarget() {
        return userName;
    }
}
