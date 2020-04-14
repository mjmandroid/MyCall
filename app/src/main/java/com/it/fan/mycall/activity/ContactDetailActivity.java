package com.it.fan.mycall.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.adapter.ContactRecordAdapter;
import com.it.fan.mycall.bean.AllCallBean;
import com.it.fan.mycall.bean.BaseAllCallBean;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ContactDetailInfo;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.util.Utility;
import com.it.fan.mycall.view.ProgressHUD;
import com.lzy.okgo.OkGo;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class ContactDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_project_name)
    TextView tv_project_name;
    @BindView(R.id.tv_patient_name)
    TextView tv_patient_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_mark)
    TextView tv_mark;
    @BindView(R.id.tv_hospital)
    TextView tv_hospital;
    @BindView(R.id.tv_doctor)
    TextView tv_doctor;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ContactDetailInfo.Detail mInfo;
    private FinishBroadCast receiver;
    private ContactRecordAdapter recordAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_detail_layout;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
        lp.topMargin = Utility.getStatusBarHeight(this)+Utility.dip2px(this,15);
        tv_title.setLayoutParams(lp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new ContactRecordAdapter();
        recyclerView.setAdapter(recordAdapter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GloableConstant.ACTION_FINISH_ACTIVITY);
        receiver = new FinishBroadCast(new WeakReference<>(this));
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if(intent != null){
            String userPhone = intent.getStringExtra("userPhone");
            String virtualPhone = intent.getStringExtra("virtualPhone");
            final ProgressHUD mLoadDialog = ProgressHUD.show(this, getString(R.string.txt_loading_mes));
            OkGo.post(Api.CONTACT_DETAIL)
                    .params("userPhone",userPhone)
                    .params("vitrualPhone",virtualPhone)
                    .execute(new JsonCallback<BaseBean<ContactDetailInfo>>() {
                        @Override
                        public void onSuccess(BaseBean<ContactDetailInfo> bean, Call call, Response response) {
                            ContactDetailInfo data = bean.getData();
                            if(data != null){
                                ContactDetailInfo.Detail addressOne = bean.getData().getAddressOne();
                                if(addressOne != null){
                                    tv_tag.setText(addressOne.getUserLabel());
                                    tv_name.setText(addressOne.getUserName());
                                    tv_project_name.setText(addressOne.getConfigName());
                                    tv_patient_name.setText(addressOne.getUserName());
                                    tv_phone.setText(addressOne.getUserPhone());
                                    tv_mark.setText(addressOne.getUserRemark());
                                    tv_hospital.setText(addressOne.getUserHospital());
                                    tv_doctor.setText(addressOne.getUserDoctor());
                                    mInfo = addressOne;
                                }
                            }
                            mLoadDialog.dismiss();
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mLoadDialog.dismiss();
                        }
                    });
            String loginPhone = SpUtil.getString(this, GloableConstant.LOGINPHONE);
            OkGo.post(Api.CALL_DETAIL)
                    .params("attacheTrue", loginPhone)
                    .params("userPhone", userPhone)
                    .params("pageNumber", 1)
                    .params("pageSize", 5)
                    .execute(new JsonCallback<BaseBean<BaseAllCallBean>>() {
                        @Override
                        public void onSuccess(BaseBean<BaseAllCallBean> baseBean, Call call, Response response) {
                            if (baseBean !=null){
                                if (baseBean.getResult() == 0){
                                    List<AllCallBean> data = baseBean.getData().getRows();
                                    recordAdapter.setNewData(data);
                                }
                            }
                        }
                    });

        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back || v.getId() == R.id.tv_back){
            finish();
        } else if(v.getId() == R.id.btn_edit){
            Intent intent = new Intent(this, EditContactActivity.class);
            if(mInfo != null){
                intent.putExtra("info",mInfo);
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    static class FinishBroadCast extends BroadcastReceiver{
        private WeakReference<ContactDetailActivity> weakReference;

        public FinishBroadCast(WeakReference<ContactDetailActivity> weakReference) {
            this.weakReference = weakReference;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ContactDetailActivity activity = weakReference.get();
            if(activity != null){
                activity.finish();
            }
        }
    }
}
