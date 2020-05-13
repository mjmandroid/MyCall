package com.it.fan.mycall.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.CallRecordAdapter;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.CallRecordBean;
import com.it.fan.mycall.bean.CallRecordListBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.util.Utility;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RecordDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    private View mHeaderView;
    private CallRecordAdapter mAdapter;
    private List<CallRecordBean> mrecordList = new ArrayList<>();
    private int type;
    private int page = 1;
    private String proId = "";

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
        proId = intent.getStringExtra("proId");
        if("-1".equals(proId)){
            proId = "";
        }
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
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page ++;
                fetchData(page);
            }
        });
    }

    @Override
    protected void initData() {

        fetchData(page);
    }


    private void fetchData(int pageNumber){
        OkGo.post(Api.TODAY_CALL_DETAIL)
                .params("attacheTrue", SpUtil.getString(this,GloableConstant.ATTACHETRUE))
                .params("proId",proId)
                .params("type",type)
                .params("pageNumber",pageNumber)
                .params("pageSize",10)
                .execute(new JsonCallback<BaseBean<CallRecordListBean>>() {
                    @Override
                    public void onSuccess(BaseBean<CallRecordListBean> callRecordListBeanBaseBean, Call call, Response response) {
                        if(callRecordListBeanBaseBean.getResult()==0){
                            if(callRecordListBeanBaseBean.getData().getRows().isEmpty()){
                                mSmartRefreshLayout.setEnableLoadMore(false);
                            } else {
                                mAdapter.addData(callRecordListBeanBaseBean.getData().getRows());
                            }
                        }
                        mSmartRefreshLayout.finishLoadMore();

                    }
                });
    }
}
