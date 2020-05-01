package com.it.fan.mycall.bean;

public class ConfigBean {

    private long createTime;
    private String deleteFlag;
    private int id;
    private String proName;
    private String vitrualPhone;
    private String configId;

    public long getCreateTime() {
        return createTime;
    }

    public ConfigBean setCreateTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public ConfigBean setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    public int getId() {
        return id;
    }

    public ConfigBean setId(int id) {
        this.id = id;
        return this;
    }

    public String getProName() {
        return proName;
    }

    public ConfigBean setProName(String proName) {
        this.proName = proName;
        return this;
    }

    public String getVitrualPhone() {
        return vitrualPhone;
    }

    public ConfigBean setVitrualPhone(String vitrualPhone) {
        this.vitrualPhone = vitrualPhone;
        return this;
    }

    public String getConfigId() {
        return configId;
    }

    public ConfigBean setConfigId(String configId) {
        this.configId = configId;
        return this;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "createTime=" + createTime +
                ", deleteFlag='" + deleteFlag + '\'' +
                ", id=" + id +
                ", proName='" + proName + '\'' +
                ", vitrualPhone='" + vitrualPhone + '\'' +
                ", configId='" + configId + '\'' +
                '}';
    }
}
