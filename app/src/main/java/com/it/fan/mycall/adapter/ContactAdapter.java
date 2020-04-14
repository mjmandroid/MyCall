package com.it.fan.mycall.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.ContactDetailActivity;
import com.it.fan.mycall.bean.ContactBean;

public class ContactAdapter extends BaseQuickAdapter<ContactBean,BaseViewHolder> {
    public ContactAdapter() {
        super(R.layout.item_contact_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ContactBean item) {
        helper.setText(R.id.tv_name,item.getUserName());
        View line = helper.getView(R.id.line);
        int position = helper.getAdapterPosition();
        String pinyin = item.getBaseIndexPinyin();
        line.setVisibility(View.VISIBLE);
        if(pinyin.length()>0){
            char current = pinyin.charAt(0);
            if((position+1) >= getItemCount()){
                line.setVisibility(View.GONE);
            } else {
                char next = getData().get(position + 1).getBaseIndexPinyin().charAt(0);
                if(current != next){
                    line.setVisibility(View.GONE);
                }
            }
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = helper.itemView.getContext();
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("userPhone",item.getUserPhone());
                intent.putExtra("virtualPhone",item.getVitrualPhone());
                context.startActivity(intent);
            }
        });
    }
}
