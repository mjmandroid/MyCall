package com.it.fan.mycall.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.AllCallBean;

public class ContactRecordAdapter extends BaseQuickAdapter<AllCallBean,BaseViewHolder> {
    public ContactRecordAdapter() {
        super(R.layout.item_contact_record_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllCallBean item) {
        helper.setText(R.id.tv_call_time,item.getShowTime());
        helper.setText(R.id.tv_call_duration,item.getCallTime());
    }
}
