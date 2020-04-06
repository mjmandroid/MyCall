package com.it.fan.mycall.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;

import razerdp.basepopup.BasePopupWindow;

public class ScreenCallRecordPop extends BasePopupWindow {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private int checkIndex = -1;

    public ScreenCallRecordPop(Context context) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_call_type_pop) {
            @Override
            protected void convert(final BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_type_name, item);
                if(checkIndex == helper.getAdapterPosition()){
                    helper.setTextColor(R.id.tv_type_name, Color.parseColor("#1C88E6"));
                } else {
                    helper.setTextColor(R.id.tv_type_name, Color.parseColor("#555555"));
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkIndex = helper.getAdapterPosition();
                        notifyDataSetChanged();
                        dismiss();
                    }
                });
            }
        };
        for (int i = 0; i < 5; i++) {
            adapter.getData().add("卫爱续航"+i);
        }
        recyclerView.setAdapter(adapter);
        findViewById(R.id.view_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout_call_type_pop);
    }
}
