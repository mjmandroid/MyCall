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
import com.it.fan.mycall.bean.ConfigBean;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;


public class CallTypePopupWindow extends BasePopupWindow {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<ConfigBean, BaseViewHolder> adapter;
    private MyTablayout tablayout;
    private int checkIndex = -1;
    private List<ConfigBean> mConfigList;
    private Callback mCallback;
    private String mDefaultItemName;

    public CallTypePopupWindow setmCallback(Callback mCallback) {
        this.mCallback = mCallback;
        return this;
    }

    public CallTypePopupWindow(Context context, final MyTablayout tablayout, List<ConfigBean> configBeanList,String mDefaultItemName) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        this.tablayout = tablayout;
        this.mConfigList = configBeanList;
        this.mDefaultItemName = mDefaultItemName;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BaseQuickAdapter<ConfigBean, BaseViewHolder>(R.layout.item_call_type_pop) {
            @Override
            protected void convert(final BaseViewHolder helper, final ConfigBean item) {
                helper.setText(R.id.tv_type_name, item.getProName());
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
                        if(mCallback != null){
                            mCallback.queryId(item);
                        }
                        dismiss();
                    }
                });
            }
        };

        if(mConfigList != null && mConfigList.size() > 0){
            adapter.getData().add(new ConfigBean().setId(-1).setProName(mDefaultItemName));
            adapter.getData().addAll(mConfigList);
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

    public interface Callback{
        void queryId(ConfigBean bean);
    }
}
