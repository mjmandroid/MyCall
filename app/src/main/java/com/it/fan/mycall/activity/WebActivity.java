package com.it.fan.mycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.Api;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {


    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.topbar_btn_left)
    TextView topbarBtnLeft;
    private String patientIdUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent.hasExtra(GloableConstant.PATIENT_NAME_DETAIL)) {
            patientIdUrl = intent.getStringExtra(GloableConstant.PATIENT_NAME_DETAIL);
        }
    }

    @Override
    protected void initListener() {
        initWebView();
    }

    @Override
    protected void initData() {

    }

    private void initWebView() {
        // JavaScript使能(如果要加载的页面中有JS代码，则必须使能JS)
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//防止https的网页打不开
        // // 设置不支持缩放
        webSettings.setSupportZoom(false);

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);// 为图片添加放大缩小功能
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setTextZoom(100);
        //适配屏幕
        /*int fontSize = (int) getResources().getDimension(R.dimen.sp_16);
        webSettings.setDefaultFontSize(fontSize);*/
/*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm.densityDpi > 240) {
            webSettings.setDefaultFontSize(40); //可以取1-72之间的任意值，默认16
        }*/

        //webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.loadUrl(patientIdUrl);
    }

    @OnClick(R.id.topbar_btn_left)
    public void onViewClicked() {
        finish();
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


    }


}
