package com.it.fan.mycall.view.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.VirtualNumAdapter;
import com.it.fan.mycall.base.BaseDialogFragment;
import com.it.fan.mycall.bean.VirtualPhoneBean;
import com.it.fan.mycall.util.CallUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectVirtualNumDialog extends BaseDialogFragment {

    private RecyclerView mRecyclerView;
    private VirtualNumAdapter virtualNumAdapter;
    private TextView tv_call;
    private String phoneNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View view = inflater.inflate(R.layout.dialog_select_virtual_num, null);
        view.findViewById(R.id.fl_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_call = view.findViewById(R.id.tv_call);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        setCancelable(true);
        initView();
        return view;
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        virtualNumAdapter = new VirtualNumAdapter();
        initData();
        mRecyclerView.setAdapter(virtualNumAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                VirtualPhoneBean bean = virtualNumAdapter.getData().get(position);
                CallUtil.call(getActivity(),phoneNum,bean.getAttacheVitrual(),bean.getConfigId());
                dismiss();
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        ArrayList<VirtualPhoneBean> datas = (ArrayList<VirtualPhoneBean>) bundle.getSerializable("list");
        if(datas != null && datas.size() > 0){
            virtualNumAdapter.setNewData(datas);
        }
        phoneNum = bundle.getString("phoneNum");
        tv_call.setText("呼叫  "+ phoneNum);

    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable());

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.CENTER;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isAdded())
            super.show(manager, tag);
    }

}
