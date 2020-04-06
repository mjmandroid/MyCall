package com.it.fan.mycall.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;

public class VirtualNumAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private Callback mCallback;
    public VirtualNumAdapter(Callback mCallback) {
        super(R.layout.item_dialog_virtual_num_layout);
        this.mCallback = mCallback;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback != null){
                    mCallback.onItemClick(v);
                }
            }
        });
    }

    public interface Callback{
        void onItemClick(View view);
    }
}
