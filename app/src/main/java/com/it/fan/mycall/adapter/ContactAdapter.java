package com.it.fan.mycall.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.ContactBean;

public class ContactAdapter extends BaseQuickAdapter<ContactBean,BaseViewHolder> {
    public ContactAdapter() {
        super(R.layout.item_contact_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        helper.setText(R.id.tv_name,item.getName());
    }
}
