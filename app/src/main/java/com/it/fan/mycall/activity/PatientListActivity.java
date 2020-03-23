package com.it.fan.mycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.PatientListAdapter;
import com.it.fan.mycall.bean.PatientBaseBean;
import com.it.fan.mycall.bean.PatientInfo;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.CallUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.view.PatientInfoPop;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PatientListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.topbar_btn_left)
    TextView mBtnLeft;
    @BindView(R.id.activity_patient_list_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_patient_list_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private PatientListAdapter mAdapter;

    private boolean isPullToRef = true;
    private int pageNum =1;
    private int loadMorepageNum =1;
    private String city;
    private String province;
    private String hospital;
    private String hospital1;
    private String drugstore;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String userName;
    private String userCall;
    private String applyforStatus;
    private List<PatientInfo> mlist= new ArrayList<>();
    private String hujingUid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_list;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        province = intent.getStringExtra("province");
        hospital1 = intent.getStringExtra("hospital");
        drugstore = intent.getStringExtra("drugstore");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        userName = intent.getStringExtra("userName");
        userCall = intent.getStringExtra("userCall");
        applyforStatus = intent.getStringExtra("applyforStatus");

        hujingUid = SpUtil.getString(this, GloableConstant.HUJINGUID);

    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //取消动画效果，防止画面闪烁
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        mAdapter = new PatientListAdapter(mlist);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);


        //上拉加载更多
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        isPullToRef = false;
                        mSwipeRefreshLayout.setEnabled(false);
                        //adapter.loadMoreEnd(true);//true is gone,false is visible

                        getData();


                    }
                });

            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PatientInfo info = mAdapter.getData().get(position);
                if (view.getId() == R.id.item_patient_list_userName){
                    Intent intent = new Intent(PatientListActivity.this,WebActivity.class);
                    intent.putExtra(GloableConstant.PATIENT_NAME_DETAIL,Api.PatientNameDetail +info.getUserId());
                    startActivity(intent);
                }else if (view.getId() ==R.id.item_patient_list_userDetail){
                    List<PatientInfo> data = mAdapter.getData();
                    if (data !=null){
                        PatientInfo patientInfo = data.get(position);
                        PatientInfoPop patientInfoPop = new PatientInfoPop(PatientListActivity.this,patientInfo.getUserId());
                        patientInfoPop.setPopupGravity(Gravity.CENTER);
                        patientInfoPop.showPopupWindow();
                    }
                }else if (view.getId()==R.id.item_patient_list_userPhone){

                    CallUtil.callDirectly(PatientListActivity.this,info.getPhone());
                }
            }
        });
    }



    @Override
    protected void initData() {
        addHeader();
        getData();
    }

    private void addHeader() {
        View mHeaderView = View.inflate(this, R.layout.item_patient_list_header, null);
        mAdapter.addHeaderView(mHeaderView);
    }

    private void getData() {

        if (isPullToRef){
            pageNum = 1;
            loadMorepageNum=1;
        }else {
            loadMorepageNum++;
            pageNum = loadMorepageNum;
        }
        OkGo.post(Api.PatientInfoQuery)
                .params("pageNumber",pageNum)
                .params("pageSize",10)
                .params("province",province)
                .params("city",city)
                .params("hospital",hospital)
                .params("drugstore",drugstore)
                .params("startAppointmentTime",startDate)
                .params("endAppointmentTime",endDate)
                .params("userName",userName)
                .params("phone",userCall)
                .params("status",applyforStatus)
                .params("hujingUid",hujingUid)
               /* .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        System.out.println(s);
                    }
                });*/
                .execute(new JsonCallback<PatientBaseBean>() {
                    @Override
                    public void onSuccess(PatientBaseBean patientBaseBean, Call call, Response response) {
                        if (patientBaseBean!=null){
                            if (patientBaseBean.getResult() == 0){
                                List<PatientInfo> rows = patientBaseBean.getData().getRows();

                                if (isPullToRef){
                                    //处理一下数据给数据分类
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.setNewData(rows);
                                    mSwipeRefreshLayout.setRefreshing(false);
                                    mAdapter.setEnableLoadMore(true);
                                }else {
                                    if (rows.size() < 10) {
                                        mAdapter.loadMoreEnd(true);
                                    }
                                    mAdapter.addData(rows);
                                    mAdapter.notifyDataSetChanged();
                                    mAdapter.loadMoreComplete();
                                    //adapter.setEnableLoadMore(false);
                                    //adapter.loadMoreEnd(true);
                                    mSwipeRefreshLayout.setEnabled(true);

                                }

                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });


    }



    @OnClick(R.id.topbar_btn_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        isPullToRef = true;

        mAdapter.setEnableLoadMore(false);
        getData();
    }
}
