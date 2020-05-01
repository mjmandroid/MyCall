package com.it.fan.mycall.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.CallRecordBean;

import java.util.List;

/**
 * Created by fan on 2019/6/7.
 */

public class CallRecordAdapter extends BaseQuickAdapter<CallRecordBean,BaseViewHolder> {
    public CallRecordAdapter(List<CallRecordBean> data) {
        super(R.layout.item_call_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CallRecordBean item) {
        helper.setText(R.id.item_call_date,item.getShowTime());
        helper.setText(R.id.item_config_name,item.getProName());
        helper.setText(R.id.item_username,item.getUserNamePat());
        helper.setText(R.id.item_real_phone,item.getPatientPhone());
        helper.setText(R.id.item_call_duration,item.getCallTime());

        //状态:1.专员未接听2.患者未接听3.回拨成功4.回拨不成功5.外呼成功6.呼入成功
        switch (item.getTelephoneStatus()){
            case 1:
                helper.setText(R.id.item_call_record_callStatus,"专员未接听");
                break;
            case 2:
                helper.setText(R.id.item_call_record_callStatus,"患者未接听");
                break;
            case 3:
                helper.setText(R.id.item_call_record_callStatus,"回拨成功");
                break;
            case 4:
                helper.setText(R.id.item_call_record_callStatus,"回拨不成功");
                break;
            case 5:
                helper.setText(R.id.item_call_record_callStatus,"外呼成功");
                break;
            case 6:
                helper.setText(R.id.item_call_record_callStatus,"呼入成功");
                break;
        }
    }
}
