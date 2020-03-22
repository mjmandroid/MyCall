package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/6/7.
 */

public class CallRecordBean {
    private String attachePhone;

    private String attachePhone_show;

    private int governId;

    private String itemName;

    private String patientPhone;

    private String patientPhone_show;

    private int telephoneStatus;


    public String getAttachePhone() {
        return attachePhone;
    }

    public void setAttachePhone(String attachePhone) {
        this.attachePhone = attachePhone;
    }

    public String getAttachePhone_show() {
        return attachePhone_show;
    }

    public void setAttachePhone_show(String attachePhone_show) {
        this.attachePhone_show = attachePhone_show;
    }

    public int getGovernId() {
        return governId;
    }

    public void setGovernId(int governId) {
        this.governId = governId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhone_show() {
        return patientPhone_show;
    }

    public void setPatientPhone_show(String patientPhone_show) {
        this.patientPhone_show = patientPhone_show;
    }

    public int getTelephoneStatus() {
        return telephoneStatus;
    }

    public void setTelephoneStatus(int telephoneStatus) {
        this.telephoneStatus = telephoneStatus;
    }
}
