package com.it.fan.mycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
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
import com.it.fan.mycall.bean.ContactDetailInfo;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.CallUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.view.ProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.Serializable;
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
    @BindView(R.id.btn_operate)
    TextView btn_operate;
    private boolean isPullToRef = true;
    private int pageNum = 1;
    private int loadMorepageNum = 1;
    private CallDetailAdapter mAdapter;
    private List<AllCallBean> mlist = new ArrayList<>();
    private String patientPhone="";
    private String virtualPhone="";
    private AllCallBean info;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent.hasExtra(GloableConstant.PATIENTPHONE)){
            patientPhone = intent.getStringExtra(GloableConstant.PATIENTPHONE);
            String showDetail = intent.getStringExtra(GloableConstant.CALL_DETAIL_SHOW);
            if(TextUtils.isEmpty(showDetail)){
                mPhoneNum.setText(patientPhone);
            } else {
                mPhoneNum.setText(showDetail);
            }
            virtualPhone = intent.getStringExtra("virtualPhone");
            info = (AllCallBean) intent.getSerializableExtra("info");
            String pro_name = info.getUserNamePat();
            if(!TextUtils.isEmpty(pro_name)){
                btn_operate.setText("编辑联系人");
            }
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
                    CallUtil.showSelectVirtualDialog(CallDetailActivity.this,allCallBean.getPatientPhone());
//                    CallUtil.call(CallDetailActivity.this,allCallBean.getPatientPhone());
                }
            }
        });
    }

    @Override
    protected void initData() {
        if(info != null){
            OkGo.post(Api.CONTACT_DETAIL)
                    .params("userPhone",patientPhone)
                    .params("vitrualPhone",info.getVitrualPhone())
                    .execute(new JsonCallback<BaseBean<ContactDetailInfo>>() {
                        @Override
                        public void onSuccess(BaseBean<ContactDetailInfo> bean, Call call, Response response) {
                            ContactDetailInfo data = bean.getData();
                            if(data != null){
                                ContactDetailInfo.Detail addressOne = bean.getData().getAddressOne();
                                if(addressOne != null){
                                    info.id = String.valueOf(addressOne.getId());
                                }
                            }

                        }
                    });
        }

        if (isPullToRef) {
            pageNum = 1;
            loadMorepageNum = 1;
        } else {
            loadMorepageNum++;
            pageNum = loadMorepageNum;
        }
        final ProgressHUD progressHUD = ProgressHUD.show(this, "正在加载...");
        String loginPhone = SpUtil.getString(this, GloableConstant.LOGINPHONE);
        OkGo.post(Api.CALL_DETAIL)
                .params("attacheTrue", virtualPhone)
                .params("userPhone", patientPhone)
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
                        progressHUD.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressHUD.dismiss();
                    }
                });
    }


    @OnClick({R.id.layout_common_title_back,R.id.btn_operate})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.layout_common_title_back:
                finish();
                break;
            case R.id.btn_operate:
                if(btn_operate.getText().toString().equals("新建联系人")){
                    Intent intent = new Intent(this,NewContactActivity.class);
                    intent.putExtra("userPhone",patientPhone);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this,EditContactActivity.class);
                    intent.putExtra("info2",info);
                    startActivity(intent);
                }
                break;
        }

    }
}
