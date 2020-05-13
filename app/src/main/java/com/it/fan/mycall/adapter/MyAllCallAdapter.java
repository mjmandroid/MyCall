package com.it.fan.mycall.adapter;

import android.graphics.Color;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.AllCallBean;

import java.util.List;

/**
 * Created by fan on 2019/6/5.
 */

public class MyAllCallAdapter extends BaseQuickAdapter<AllCallBean,BaseViewHolder> {
    public MyAllCallAdapter(List<AllCallBean> data) {
        super(R.layout.item_all_call, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllCallBean item) {
        helper.setText(R.id.item_all_call_time,item.getShowTime());
        helper.addOnClickListener(R.id.item_all_call_detail);
        //状态:1.专员未接听2.患者未接听3.回拨成功4.回拨不成功5.外呼成功6.呼入成功
        if (item.getTelephoneStatus()==1){
            helper.setTextColor(R.id.item_all_call_phoneNum, Color.parseColor("#f15656"));
        }else {
            helper.setTextColor(R.id.item_all_call_phoneNum, Color.parseColor("#333333"));
        }
        if(TextUtils.isEmpty(item.getProName())){
            if(item.getCallCount()>1){
                helper.setText(R.id.item_all_call_phoneNum,item.getPatientPhone()+"("+item.getCallCount()+")");
            } else {
                helper.setText(R.id.item_all_call_phoneNum,item.getPatientPhone());
            }
        } else {
            String content = "";
            if(!TextUtils.isEmpty(item.getProName())){
                content = item.getProName();
            }
            if(!TextUtils.isEmpty(item.getUserNamePat())){
                content = content + "-"+item.getUserNamePat();
            } else {
                content = content + "-"+item.getPatientPhone();
                if(item.getCallCount() > 1){
                    content = content + "(" + item.getCallCount() + ")";
                }
                helper.setText(R.id.item_all_call_phoneNum,content);
                return;
            }
            if(!TextUtils.isEmpty(item.getUserLabel())){
                content = content +"-"+item.getUserLabel();
            }
            if(!TextUtils.isEmpty(item.getUserRemark())){
                content = content + "-" + item.getUserRemark();
            }
            if(item.getCallCount() > 1){
                content = content + "(" + item.getCallCount() + ")";
            }
            helper.setText(R.id.item_all_call_phoneNum,content);
        }


    }
}
