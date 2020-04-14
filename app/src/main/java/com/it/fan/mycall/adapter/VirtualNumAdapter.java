package com.it.fan.mycall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.VirtualPhoneBean;

public class VirtualNumAdapter extends BaseQuickAdapter<VirtualPhoneBean,BaseViewHolder> {

    private int[] call_icon_res = {R.mipmap.call_1,R.mipmap.call_2};

    public VirtualNumAdapter() {
        super(R.layout.item_dialog_virtual_num_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, VirtualPhoneBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_phone = helper.getView(R.id.tv_phone);
        ImageView iv_call = helper.getView(R.id.iv_call);
        tv_name.setText(item.getUserName());
        tv_phone.setText(item.getAttacheVitrual());
        int position = helper.getAdapterPosition();
        if(position < call_icon_res.length){
            iv_call.setImageResource(call_icon_res[position]);
        } else {
            iv_call.setImageResource(call_icon_res[1]);
        }
    }

}
