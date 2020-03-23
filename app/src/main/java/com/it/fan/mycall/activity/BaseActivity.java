package com.it.fan.mycall.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/12/12.
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    public Context mcontext = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }


    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();
}
