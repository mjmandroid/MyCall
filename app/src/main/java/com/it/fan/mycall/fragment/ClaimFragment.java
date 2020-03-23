package com.it.fan.mycall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by fan on 2019/9/12.
 */

public class ClaimFragment extends BaseFragment {
    @BindView(R.id.fragment_claim_webView)
    WebView mWebView;
    @BindView(R.id.fragment_claim_editText)
    EditText mEditText;
    @BindView(R.id.fragment_claim_enterText)
    TextView mEnterText;
    Unbinder unbinder;


    @Override
    protected int getLayout() {
        return R.layout.fragment_claim;
    }

    @Override
    protected void initView() {
        initWebView();
    }

    @Override
    protected void initListener() {

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
        mWebView.setWebViewClient(new MyWebViewClient());
        String ding_url = SpUtil.getString(getContext(), GloableConstant.DING_URL);
        mWebView.loadUrl(ding_url);
    }





    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


    }

    public boolean onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return false;
    }


}
