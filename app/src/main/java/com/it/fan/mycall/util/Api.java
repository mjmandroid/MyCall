package com.it.fan.mycall.util;

/**
 * Created by fan on 2019/6/4.
 */

public interface Api {
    //String TEST = "http://126test.mdmooc.com/hzzc";//测试
    //String TEST = "https://apionline.mdmooc.org/hzzc";//测试1
    //String TEST = "https://liyizhe.mynatapp.cc/hzzcc";//测试2
    String TEST = "https://apionline.mdmooc.org/hzzc";//线上

    String LOGIN = TEST +"/mobile/main/loginCheck";
    String ALLCALL = TEST +"/mobile/main/allCalls";//全部通话
    String MISSEDCALL = TEST +"/mobile/main/missedCall";//未接来电
    String CALLDETAIL = TEST +"/mobile/main/detail";//未接来电
    String CALLRECORD = TEST +"/mobile/main/inquiryRecord";//话单记录
    String BINDCALL = TEST +"/mobile/main/bound";//绑定手机号和虚拟号
    String MISSCOUNT = TEST +"/mobile/main/missCount";//未接来电数量
    String PatientInfoQuery = TEST +"/assistance/patientList";//患者信息查询
    String PatientInfoDetail = TEST +"/assistance/applicationDetails";//患者详情
    //String PatientNameDetail = "https://www.sfdev.fun/devdaz/DAZPatient?id=";//患者ID
    String PatientNameDetail = "http://darzlexpap.cfc1984.com/prddaz/DAZPatient?id=";//患者ID
    //String PatientDetailH5 = "https://www.sfdev.fun/devdaz/DAZPatientRequest?pid=";//患者ID&rid=援助申请ID
    String PatientDetailH5 = "http://darzlexpap.cfc1984.com/prddaz/DAZPatientRequest?pid=";//患者ID&rid=援助申请ID
}
