package com.it.fan.mycall.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.CallRecordAdapter;
import com.it.fan.mycall.bean.CallRecordBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private View mHeaderView;
    private CallRecordAdapter mAdapter;
    private List<CallRecordBean> mrecordList = new ArrayList<>();
    private int type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_detail_layout;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new CallRecordAdapter(mrecordList);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mHeaderView = View.inflate(this, R.layout.item_call_record_header, null);
        mHeaderView.findViewById(R.id.ll_top).setVisibility(View.GONE);
        mAdapter.addHeaderView(mHeaderView);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
        lp.topMargin = Utility.getStatusBarHeight(this)+Utility.dip2px(this,15);
        tv_title.setLayoutParams(lp);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        if(type == GloableConstant.CALL_IN_TYPE){
            tv_title.setText("呼入详情");
        } else if(type == GloableConstant.CALL_OUT_TYPE){
            tv_title.setText("呼出详情");
        } else if(type == GloableConstant.CALL_LOSS_TYPE){
            tv_title.setText("呼损详情");
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
            case R.id.tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
