package com.it.fan.mycall.util;

/**
 * Created by fan on 2019/9/12.
 */

public class StringUtil {
    public static String[] sortStrs = {"全部","预约", "待审批", "待完善资料", "审批通过", "拒绝", "领药完成", "取消",
            "待审批", "审批通过", "已提交至DTC", "待接收", "拒绝","领用完成","取消"};
    public static String[] paramsStrs = {"","Booked", "Waiting for Approval", "Waiting for Update",
            "Confirmed", "Rejected", "Completed", "Cancelled", "Pending for Approval", "Confirmed by Charity",
            "Submited to DTC", "Waiting for Receiving", "Rejected","Completed","Cancelled"};
    public static String[] introStrs = {"","患者微信端进行预约", "患者提交正式援助申请", "项目办审批时，标记成该援助申请需完善材料",
            "援助申请审批通过", "援助申请审批拒绝", "患者用药完成", "取消该次援助申请（项目办后台功能)",
            "患者提交预约配送申请", "预约配送审批通过",
            "患者的预约配送信息已传递至DTC", "DTC已开始配送，等待患者接收", "项目办审批不通过",
            "患者接收配送并完成扫码","取消了本次预约配送"};
}
