package com.it.fan.mycall.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.PatientInfo;

import java.util.List;

/**
 * Created by fan on 2019/9/4.
 */

public class PatientListAdapter extends BaseQuickAdapter<PatientInfo,BaseViewHolder> {
    public PatientListAdapter(List<PatientInfo> data) {
        super(R.layout.item_patient_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientInfo item) {
        helper.setText(R.id.item_patient_list_userName,item.getUserName());
        helper.setText(R.id.item_patient_list_userNum,item.getEntryNumber());
        helper.addOnClickListener(R.id.item_patient_list_userName);
        helper.addOnClickListener(R.id.item_patient_list_userDetail);
        helper.addOnClickListener(R.id.item_patient_list_userPhone);

        //性别  1男  2女
        if ("1".equals(item.getSex())){
            helper.setText(R.id.item_patient_list_userSex,"男");
        }else if ("2".equals(item.getSex())){
            helper.setText(R.id.item_patient_list_userSex,"女");
        }

        helper.setText(R.id.item_patient_list_userAge,item.getAgeI()+"");
        helper.setText(R.id.item_patient_list_userPhone,item.getPhone());
        helper.setText(R.id.item_patient_list_userCount,item.getAssistanceCount()+"次");
        helper.setText(R.id.item_patient_list_userDetail,"查看");

    }
}
