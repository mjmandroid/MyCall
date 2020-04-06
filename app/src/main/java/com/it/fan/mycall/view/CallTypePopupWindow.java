package com.it.fan.mycall.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.it.fan.mycall.R;

import razerdp.basepopup.BasePopupWindow;


public class CallTypePopupWindow extends BasePopupWindow {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private MyTablayout tablayout;
    private int checkIndex = -1;

    public CallTypePopupWindow(Context context,final MyTablayout tablayout) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        this.tablayout = tablayout;
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
                        tablayout.restoreArrow();
                        checkIndex = helper.getAdapterPosition();
                        notifyDataSetChanged();
                        dismiss();
                    }
                });
            }
        };
        for (int i = 0; i < 5; i++) {
            adapter.getData().add("全部电话");
        }
        recyclerView.setAdapter(adapter);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                tablayout.restoreArrow();
            }
        });
        findViewById(R.id.view_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                tablayout.restoreArrow();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.layout_call_type_pop);
    }
}
