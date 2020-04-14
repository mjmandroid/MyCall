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
import com.it.fan.mycall.bean.ConfigBean;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

public class ScreenCallRecordPop extends BasePopupWindow {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<ConfigBean, BaseViewHolder> adapter;
    private int checkIndex = -1;
    private List<ConfigBean> mConfigBeans;
    private IItemSelectListener mItemSelectListener;
    private boolean isAddDefalut = true;

    public ScreenCallRecordPop(Context context,List<ConfigBean> mConfigBeans) {
        this(context,mConfigBeans,true);
    }
    public ScreenCallRecordPop(Context context,List<ConfigBean> mConfigBeans,boolean isAddDefalut) {
        super(context, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        this.mConfigBeans = mConfigBeans;
        this.isAddDefalut = isAddDefalut;
        initView();
    }

    public void setmItemSelectListener(IItemSelectListener mItemSelectListener) {
        this.mItemSelectListener = mItemSelectListener;
    }

    private void initView() {
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
                        dismiss();
                        if(mItemSelectListener != null){
                            mItemSelectListener.onSelect(item);
                        }
                    }
                });
            }
        };
        if(mConfigBeans != null && mConfigBeans.size() > 0){
            if(isAddDefalut){
                adapter.getData().add(new ConfigBean().setProName("全部").setId(-1));
            }
            adapter.getData().addAll(mConfigBeans);
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

    public interface IItemSelectListener<T>{
        void onSelect(T data);
    }
}
