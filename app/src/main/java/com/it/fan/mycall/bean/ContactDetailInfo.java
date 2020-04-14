package com.it.fan.mycall.bean;

import java.io.Serializable;
import java.util.List;

public class ContactDetailInfo {

    private Detail addressOne;


    public Detail getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(Detail addressOne) {
        this.addressOne = addressOne;
    }

    public static class ConfigInfo{
        private String appKey;
        private String deleteFlag;
        private int id;
        private String name;

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(String deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Detail implements Serializable{
        private int configId;
        private String configName;
        private int id;
        private String proId;
        private String proName;
        private String userDoctor;
        private String userHospital;
        private String userLabel;
        private String userName;
        private String userPhone;
        private String userPrefix;
        private String userRemark;
        private String vitrualPhone;

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


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
