package com.it.fan.mycall.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.util.Utility;

import butterknife.BindView;

public class EditContactActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

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
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
