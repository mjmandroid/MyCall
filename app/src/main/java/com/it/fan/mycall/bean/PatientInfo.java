package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/9/4.
 */

public class PatientInfo {
    private long age;

    private long ageI;

    private String entryNumber;

    private String hospital;

    private String phone;

    private String sex;

    private String userId;

    private String userName;
    private String assistanceCount;

    public String getAssistanceCount() {
        return assistanceCount;
    }

    public void setAssistanceCount(String assistanceCount) {
        this.assistanceCount = assistanceCount;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public long getAgeI() {
        return ageI;
    }

    public void setAgeI(long ageI) {
        this.ageI = ageI;
    }

    public String getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(String entryNumber) {
        this.entryNumber = entryNumber;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
