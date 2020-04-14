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
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.Utility;
import com.it.fan.mycall.view.MyApp;
import com.it.fan.mycall.view.ProgressHUD;
import com.it.fan.mycall.view.ScreenCallRecordPop;
import com.lzy.okgo.OkGo;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class NewContactActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_select_pro)
    TextView tv_select_pro;
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
    private List<ConfigBean> configBeanList;
    private ConfigBean mConfigBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newcontact_layout;
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
        fl_self.setOnClickListener(this);
        fl_home.setOnClickListener(this);
        fl_other.setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_select_pro).setOnClickListener(this);

    }

    @Override
    protected void initData() {
        OkGo.post(Api.CONFIG_INFO).execute(new JsonCallback<BaseBean<List<ConfigBean>>>() {
            @Override
            public void onSuccess(BaseBean<List<ConfigBean>> listBaseBean, Call call, Response response) {
                configBeanList = listBaseBean.getData();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select_pro:
                ScreenCallRecordPop configPop = new ScreenCallRecordPop(this, configBeanList,false);
                configPop.setBackgroundColor(0x0);
                configPop.setmItemSelectListener(new ScreenCallRecordPop.IItemSelectListener<ConfigBean>() {
                    @Override
                    public void onSelect(ConfigBean data) {
                        tv_select_pro.setText(data.getProName());
                        mConfigBean = data;

                    }
                });
                configPop.showPopupWindow(tv_select_pro);
                break;
        }

    }

    private void save() {
        if(TextUtils.isEmpty(tv_select_pro.getText().toString())){
            Toast.makeText(this,"请选择项目",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mConfigBean == null){
            return;
        }
        String userPhone = et_phone.getText().toString().trim();
        String userDoctor = et_doctor.getText().toString().trim();
        String userName = et_patient_name.getText().toString().trim();
        String userRemark = et_mark.getText().toString().trim();
        String label = "";
        if(flags[0]){
            label = "本人";
        } else if(flags[1]){
            label = "家属";
        } else if(flags[2]){
            label = "其他";
        }
        final ProgressHUD progressHUD = ProgressHUD.show(this, "正在保存...");
        OkGo.post(Api.CONTACT_INFO_SAVE)
                .params("configId",mConfigBean.getConfigId())
                .params("proId",mConfigBean.getId())
                .params("vitrualPhone",mConfigBean.getVitrualPhone())
                .params("userPhone",userPhone)
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
                            progressHUD.dismiss();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        progressHUD.dismiss();
                    }
                });

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
