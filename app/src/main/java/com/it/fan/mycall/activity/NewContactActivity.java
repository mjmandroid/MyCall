package com.it.fan.mycall.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.util.Utility;

import butterknife.BindView;

public class NewContactActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.fl_self)
    FrameLayout fl_self;
    @BindView(R.id.fl_home)
    FrameLayout fl_home;
    @BindView(R.id.fl_other)
    FrameLayout fl_other;

    private boolean[] flags = {true,false,false};

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

    }

    @Override
    protected void initData() {

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
                startActivity(new Intent(this,ContactDetailActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
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
