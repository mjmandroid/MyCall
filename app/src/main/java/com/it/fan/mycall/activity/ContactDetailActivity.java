package com.it.fan.mycall.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.util.Utility;

import butterknife.BindView;

public class ContactDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tv_title;

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
    }

    @Override
    protected void initListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_back || v.getId() == R.id.tv_back){
            finish();
        } else if(v.getId() == R.id.btn_edit){
            startActivity(new Intent(this,EditContactActivity.class));
        }
    }
}
