package com.it.fan.mycall.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.AllCallBean;

import java.util.List;

/**
 * Created by fan on 2019/6/5.
 */

public class CallDetailAdapter extends BaseQuickAdapter<AllCallBean,BaseViewHolder> {
    public CallDetailAdapter(List<AllCallBean> data) {
        super(R.layout.item_call_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllCallBean item) {
        helper.setText(R.id.item_call_detail_date,item.getShowTime());
        helper.setText(R.id.item_call_detail_time,item.getCallTime());

        //状态:1.专员未接听2.患者未接听3.回拨成功4.回拨不成功5.外呼成功6.呼入成功
        switch (item.getTelephoneStatus()){
            case 1:
                helper.setText(R.id.item_call_detail_phoneStatus,"专员未接听");
                break;
            case 2:
                helper.setText(R.id.item_call_detail_phoneStatus,"患者未接听");
                break;
            case 3:
                helper.setText(R.id.item_call_detail_phoneStatus,"回拨成功");
                break;
            case 4:
                helper.setText(R.id.item_call_detail_phoneStatus,"回拨不成功");
                break;
            case 5:
                helper.setText(R.id.item_call_detail_phoneStatus,"外呼成功");
                break;
            case 6:
                helper.setText(R.id.item_call_detail_phoneStatus,"呼入成功");
                break;
        }
    }
}
