package com.it.fan.mycall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fan on 2019/9/4.
 */

public class PatientDeatilBean {

    /**
     * result : 0
     * msg : 查询成功
     * data : [{"appointmentDateStr":"2019-09-02","appointmentDateTimeStr":"2019-09-02 11:38:22","appointmentNumber":"","assistanceNumber":"00001327","doctors":"张医生","drugstore":"123大药房","hospital":"北京望京医院","number":1,"status":"1","userId":"0030l00000UlvDyAAJ","userName":"王睿"},{"appointmentNumber":"","assistanceNumber":"00001327","doctors":"张医生","drugstore":"456大药房","hospital":"北京abc医院","number":2,"status":"1","userId":"0030l00000UlvDyAAJ","userName":"王睿"}]
     */

    private int result;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * appointmentDateStr : 2019-09-02
         * appointmentDateTimeStr : 2019-09-02 11:38:22
         * appointmentNumber :
         * assistanceNumber : 00001327
         * doctors : 张医生
         * drugstore : 123大药房
         * hospital : 北京望京医院
         * number : 1
         * status : 1
         * userId : 0030l00000UlvDyAAJ
         * userName : 王睿
         */

        private String appointmentDateStr;
        private String appointmentDateTimeStr;
        private String appointmentNumber;
        private String assistanceNumber;
        private String doctors;
        private String drugstore;
        private String hospital;
        private int number;
        private String status;
        private String userId;
        private String userName;

        public String getAppointmentDateStr() {
            return appointmentDateStr;
        }

        public void setAppointmentDateStr(String appointmentDateStr) {
            this.appointmentDateStr = appointmentDateStr;
        }

        public String getAppointmentDateTimeStr() {
            return appointmentDateTimeStr;
        }

        public void setAppointmentDateTimeStr(String appointmentDateTimeStr) {
            this.appointmentDateTimeStr = appointmentDateTimeStr;
        }

        public String getAppointmentNumber() {
            return appointmentNumber;
        }

        public void setAppointmentNumber(String appointmentNumber) {
            this.appointmentNumber = appointmentNumber;
        }

        public String getAssistanceNumber() {
            return assistanceNumber;
        }

        public void setAssistanceNumber(String assistanceNumber) {
            this.assistanceNumber = assistanceNumber;
        }

        public String getDoctors() {
            return doctors;
        }

        public void setDoctors(String doctors) {
            this.doctors = doctors;
        }

        public String getDrugstore() {
            return drugstore;
        }

        public void setDrugstore(String drugstore) {
            this.drugstore = drugstore;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
}
