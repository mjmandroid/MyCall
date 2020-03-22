package com.it.fan.mycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.CallDetailAdapter;
import com.it.fan.mycall.adapter.MyAllCallAdapter;
import com.it.fan.mycall.bean.AllCallBean;
import com.it.fan.mycall.bean.BaseAllCallBean;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.CallUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class CallDetailActivity extends BaseActivity {

    @BindView(R.id.layout_common_title_back)
    RelativeLayout mBack;
    @BindView(R.id.activity_call_detail_phoneNum)
    TextView mPhoneNum;
    @BindView(R.id.activity_call_detail_recyclerView)
    RecyclerView mRecyclerView;
    private boolean isPullToRef = true;
    private int pageNum = 1;
    private int loadMorepageNum = 1;
    private CallDetailAdapter mAdapter;
    private List<AllCallBean> mlist = new ArrayList<>();
    private String patientPhone="";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent.hasExtra(GloableConstant.PATIENTPHONE)){
            patientPhone = intent.getStringExtra(GloableConstant.PATIENTPHONE);
            mPhoneNum.setText(patientPhone);
        }
    }

    @Override
    protected void initListener() {
        //取消动画效果，防止画面闪烁
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new CallDetailAdapter(mlist);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AllCallBean> data = mAdapter.getData();
                if (data!=null){
                    AllCallBean allCallBean = data.get(position);
                    CallUtil.call(CallDetailActivity.this,allCallBean.getPatientPhone());
                }
            }
        });
    }

    @Override
    protected void initData() {

        if (isPullToRef) {
            pageNum = 1;
            loadMorepageNum = 1;
        } else {
            loadMorepageNum++;
            pageNum = loadMorepageNum;
        }
        String loginPhone = SpUtil.getString(this, GloableConstant.LOGINPHONE);
        OkGo.post(Api.CALLDETAIL)
                .params("attachePhone", loginPhone)
                .params("patientPhone", patientPhone)
                .params("pageNumber", 1)
                .params("pageSize", 20)
                .execute(new JsonCallback<BaseBean<BaseAllCallBean>>() {
                    @Override
                    public void onSuccess(BaseBean<BaseAllCallBean> baseBean, Call call, Response response) {
                        if (baseBean !=null){
                            if (baseBean.getResult() == 0){
                                List<AllCallBean> data = baseBean.getData().getRows();
                                mAdapter.setNewData(data);
                                mAdapter.notifyDataSetChanged();

                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }


    @OnClick(R.id.layout_common_title_back)
    public void onViewClicked() {
        finish();
    }
}
