package com.it.fan.mycall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.AllCallBean;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.bean.ContactDetailInfo;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.util.Utility;
import com.it.fan.mycall.view.MyApp;
import com.it.fan.mycall.view.ScreenCallRecordPop;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class EditContactActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_project_name)
    TextView tv_project_name;
    @BindView(R.id.et_patient_name)
    EditText et_patient_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_mark)
    EditText et_mark;
    @BindView(R.id.et_hospital)
    EditText et_hospital;
    @BindView(R.id.et_doctor)
    EditText et_doctor;
    @BindView(R.id.fl_self)
    FrameLayout fl_self;
    @BindView(R.id.fl_home)
    FrameLayout fl_home;
    @BindView(R.id.fl_other)
    FrameLayout fl_other;
    private boolean[] flags = {true,false,false};
    private ContactDetailInfo.Detail mInfo;
    private List<ConfigBean> configBeanList;
    private ConfigBean mConfigBean;
    private AllCallBean allCallBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_contact_layout;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
        lp.topMargin = Utility.getStatusBarHeight(this)+Utility.dip2px(this,15);
        tv_title.setLayoutParams(lp);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        OkGo.post(Api.CONFIG_INFO)
                .params("attacheTrue", SpUtil.getString(this,GloableConstant.ATTACHETRUE))
                .execute(new JsonCallback<BaseBean<List<ConfigBean>>>() {
                    @Override
                    public void onSuccess(BaseBean<List<ConfigBean>> listBaseBean, Call call, Response response) {
                        configBeanList = listBaseBean.getData();
                    }
                });
        if(getIntent() != null){
            mInfo = (ContactDetailInfo.Detail) getIntent().getSerializableExtra("info");
            if(mInfo != null){
                tv_project_name.setText(mInfo.getProName());
                et_patient_name.setText(mInfo.getUserName());
                et_phone.setText(mInfo.getUserPhone());
                et_mark.setText(mInfo.getUserRemark());
                et_hospital.setText(mInfo.getUserHospital());
                et_doctor.setText(mInfo.getUserDoctor());
                if("本人".equals(mInfo.getUserLabel())){
                    flags = new boolean[]{true,false,false};
                } else if("家属".equals(mInfo.getUserLabel())){
                    flags = new boolean[]{false,true,false};
                } else {
                    flags = new boolean[]{false,false,true};
                }
                changeState();
            } else {
                allCallBean = (AllCallBean) getIntent().getSerializableExtra("info2");
                tv_project_name.setText(allCallBean.getProName());
                et_patient_name.setText(allCallBean.getUserNamePat());
                et_phone.setText(allCallBean.getUserPhone());
                et_mark.setText(allCallBean.getUserRemark());
                et_hospital.setText(allCallBean.getUserHospital());
                et_doctor.setText(allCallBean.getUserDoctor());
                if("本人".equals(allCallBean.getUserLabel())){
                    flags = new boolean[]{true,false,false};
                } else if("家属".equals(allCallBean.getUserLabel())){
                    flags = new boolean[]{false,true,false};
                } else {
                    flags = new boolean[]{false,false,true};
                }
                changeState();
            }
        }
    }

    @OnClick({R.id.iv_back,R.id.tv_back,R.id.fl_self,R.id.fl_home,R.id.fl_other,R.id.btn_save,R.id.tv_project_name})
    public void onViewClick(View v){
        switch (v.getId()){
            case R.id.tv_back:
            case R.id.iv_back:
                finish();
                break;
            case R.id.fl_self:
                flags[0] = !flags[0];
                flags[1] = false;
                flags[2] = false;
                changeState();
                break;
            case R.id.fl_home:
                flags[1] = !flags[1];
                flags[0] = false;
                flags[2] = false;
                changeState();
                break;
            case R.id.fl_other:
                flags[2] = !flags[2];
                flags[0] = false;
                flags[1] = false;
                changeState();
                break;
            case R.id.btn_save:
                save();
                break;
            case R.id.tv_project_name:
                ScreenCallRecordPop configPop = new ScreenCallRecordPop(this, configBeanList,false);
                configPop.setBackgroundColor(0x0);
                configPop.setmItemSelectListener(new ScreenCallRecordPop.IItemSelectListener<ConfigBean>() {
                    @Override
                    public void onSelect(ConfigBean data) {
                        tv_project_name.setText(data.getProName());
                        mConfigBean = data;

                    }
                });
                int[] los = new int[2];
                tv_project_name.getLocationOnScreen(los);
                configPop.showPopupWindow(0,los[1]+tv_project_name.getHeight());
                break;
        }
    }

    private void save() {
        String userPhone = et_phone.getText().toString().trim();
        String userDoctor = et_doctor.getText().toString().trim();
        String userName = et_patient_name.getText().toString().trim();
        String userRemark = et_mark.getText().toString().trim();
        String label = "";
        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(flags[0]){
            label = "本人";
        } else if(flags[1]){
            label = "家属";
        } else if(flags[2]){
            label = "其他";
        }
        if(mConfigBean != null){
            if(TextUtils.isEmpty(mConfigBean.getVitrualPhone())){
                Utility.showToast("没有关联此项目");
                return;
            }
        }
        if(mInfo != null){
            OkGo.post(Api.CONTACT_INFO_SAVE)
                    .params("configId",mInfo.getConfigId())
                    .params("proId",mConfigBean == null ? mInfo.getProId() : mConfigBean.getId()+"")
                    .params("vitrualPhone",mConfigBean == null ? mInfo.getVitrualPhone() : mConfigBean.getVitrualPhone())
                    .params("userPhone",userPhone)
                    .params("id",mInfo.getId())
                    .params("userDoctor",userDoctor)
                    .params("userLabel",label)
                    .params("userHospital",et_hospital.getText().toString().trim())
                    .params("userName",userName)
                    .params("userRemark",userRemark)
                    .execute(new JsonCallback<BaseBean<Object>>() {
                        @Override
                        public void onSuccess(BaseBean<Object> objectBaseBean, Call call, Response response) {
                            if(objectBaseBean.getResult() == 0){
                                Toast.makeText(MyApp.getInstance(),"保存成功",Toast.LENGTH_SHORT).show();
                                finish();
                                sendBroadcast(new Intent(GloableConstant.ACTION_FINISH_ACTIVITY));
                            }
                        }
                    });
        } else if(allCallBean != null){
            OkGo.post(Api.CONTACT_INFO_SAVE)
                    .params("configId",allCallBean.getConfigId())
                    .params("proId",mConfigBean == null ? allCallBean.getProId() : mConfigBean.getId()+"")
                    .params("vitrualPhone",mConfigBean == null ? allCallBean.getVitrualPhone() : mConfigBean.getVitrualPhone())
                    .params("userPhone",userPhone)
                    .params("id",allCallBean.id)
                    .params("userDoctor",userDoctor)
                    .params("userLabel",label)
                    .params("userHospital",et_hospital.getText().toString().trim())
                    .params("userName",userName)
                    .params("userRemark",userRemark)
                    .execute(new JsonCallback<BaseBean<Object>>() {
                        @Override
                        public void onSuccess(BaseBean<Object> objectBaseBean, Call call, Response response) {
                            if(objectBaseBean.getResult() == 0){
                                Toast.makeText(MyApp.getInstance(),"保存成功",Toast.LENGTH_SHORT).show();
                                finish();
                                sendBroadcast(new Intent(GloableConstant.ACTION_FINISH_ACTIVITY));
                            }
                        }
                    });
        }
    }

    private void changeState(){
        if(flags[0]){
            fl_self.getChildAt(1).setVisibility(View.VISIBLE);
        } else {
            fl_self.getChildAt(1).setVisibility(View.INVISIBLE);
        }
        if(flags[1]){
            fl_home.getChildAt(1).setVisibility(View.VISIBLE);
        } else {
            fl_home.getChildAt(1).setVisibility(View.INVISIBLE);
        }
        if(flags[2]){
            fl_other.getChildAt(1).setVisibility(View.VISIBLE);
        } else {
            fl_other.getChildAt(1).setVisibility(View.INVISIBLE);
        }
    }

}
