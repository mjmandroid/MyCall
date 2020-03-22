package com.it.fan.mycall.bean;

/**
 * Created by fan on 2019/6/5.
 */

public class AllCallBean {
    private String attachePhone;

    private long begin_time;

    private int callCount;

    private String patientPhone;

    private String showTime;

    private int telephoneStatus;

    private String callTime;

    private int call_duration;

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public int getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(int call_duration) {
        this.call_duration = call_duration;
    }

    public String getAttachePhone() {
        return attachePhone;
    }

    public void setAttachePhone(String attachePhone) {
        this.attachePhone = attachePhone;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getTelephoneStatus() {
        return telephoneStatus;
    }

    public void setTelephoneStatus(int telephoneStatus) {
        this.telephoneStatus = telephoneStatus;
    }
}
