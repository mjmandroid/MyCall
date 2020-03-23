package com.it.fan.mycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.LoginResultBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.service.MessageCountService;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.BrageUtil;
import com.it.fan.mycall.util.GlobalUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.view.ProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.activity_new_login_userName)
    EditText mUserName;
    @BindView(R.id.activity_new_login_line)
    View activityNewLoginLine;
    @BindView(R.id.activity_new_login_userPwd)
    EditText mUserPwd;
    @BindView(R.id.activity_new_login_go)
    TextView mLoginGo;
    private ProgressHUD mLoadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        //jump2MainActivity();

        String phone = SpUtil.getString(this, GloableConstant.LOGINPHONE);
        String pwd = SpUtil.getString(this, GloableConstant.LOGINPWD);

        if (!TextUtils.isEmpty(phone)){
            mUserName.setText(phone);
            mUserPwd.setText(pwd);
        }

        //ShortcutBadger.applyCount(this, 5);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.activity_new_login_go)
    public void onViewClicked() {

        go2Login();
    }

    private void go2Login() {
        final String userMobile = mUserName.getText().toString().trim();
        final String paw = mUserPwd.getText().toString().trim();

        if (TextUtils.isEmpty(userMobile)) {

            GlobalUtil.shortToast(this, "账号不能为空");
            return;
        }

        if (TextUtils.isEmpty(paw)) {
            GlobalUtil.shortToast(this, "密码不能为空");
            return;
        }
        //jump2MainActivity();
        mLoadingDialog = ProgressHUD.show(this, getString(R.string.txt_loading_mes));
        OkGo.post(Api.LOGIN)
                .params("attachePhone", userMobile)
                .params("password", paw)

                .execute(new JsonCallback<BaseBean<LoginResultBean>>() {

                    @Override
                    public void onSuccess(BaseBean<LoginResultBean> baseBean, Call call, Response response) {
                        if (baseBean!=null){
                            if (baseBean.getResult()==0){
                                SpUtil.SaveString(LoginActivity.this, GloableConstant.ATTACHETRUE,baseBean.getData().getAttacheTrue());
                                SpUtil.SaveString(LoginActivity.this, GloableConstant.ATTACHEVITRUAL,baseBean.getData().getAttacheVitrual());
                                SpUtil.SaveString(LoginActivity.this, GloableConstant.USERNAME,baseBean.getData().getUserName());
                                SpUtil.SaveString(LoginActivity.this,GloableConstant.LOGINPHONE,userMobile);
                                SpUtil.SaveString(LoginActivity.this,GloableConstant.LOGINPWD,paw);
                                SpUtil.SaveString(LoginActivity.this,GloableConstant.HUJINGUID,baseBean.getData().getHujingUid());
                                SpUtil.SaveString(LoginActivity.this,GloableConstant.DING_URL,baseBean.getData().getDing_url());
                                jump2MainActivity();
                            }else {
                                Toast.makeText(LoginActivity.this,baseBean.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        mLoadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mLoadingDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void jump2MainActivity() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
