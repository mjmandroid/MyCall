package com.it.fan.mycall.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.fragment.CallRecordFragment;
import com.it.fan.mycall.fragment.ClaimFragment;
import com.it.fan.mycall.fragment.ContactsFragment;
import com.it.fan.mycall.fragment.PatientInfoQueryFragment;
import com.it.fan.mycall.fragment.PlayCallFragment;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.service.JobSchedulerService;
import com.it.fan.mycall.service.TracePhoneService;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.CallUtil;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.LogUtils;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.util.Utility;
import com.it.fan.mycall.view.ScreenCallRecordPop;
import com.it.fan.mycall.view.VirtualKeyboardView;
import com.lzy.okgo.OkGo;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AutoLayoutActivity {

    private static final int REQUEST_CODE = 100;
    private VirtualKeyboardView mVirtualKeyBoard;
    private ArrayList<Map<String, String>> valueList;
    private EditText textAmount;
    private GridView gridView;
    private TextView tv_title;

    private String[] mTitles = {"拨号", "话单记录","患者信息查询"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
//    private SegmentTabLayout mTablayout;
    final public static int REQUEST_CODE_ASK_CALL_PHONE=123;
    private Fragment mContent;
    private boolean isdialogshow = false;
    protected String[] needPermissions = {
            android.Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.CALL_PHONE
    };
    private ImageView iv_arrow;
    private List<ConfigBean> configBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
//        Utility.setActionBar(this, R.color.text999999);
        setContentView(R.layout.activity_main_layout);
        initView();
        initListener();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isdialogshow)
            requestPermiss(needPermissions);
    }

    private void requestPermiss(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    REQUEST_CODE_ASK_CALL_PHONE);
        } else {
            startService();
        }
    }



    private void initView() {
//        mTablayout = (SegmentTabLayout) findViewById(R.id.activity_main_tabLayout);
        tv_title = findViewById(R.id.tv_title);
        iv_arrow = findViewById(R.id.iv_arrow);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
        lp.topMargin = Utility.getStatusBarHeight(this)+Utility.dip2px(this,15);
        tv_title.setLayoutParams(lp);
        RelativeLayout.LayoutParams ip = (RelativeLayout.LayoutParams) iv_arrow.getLayoutParams();
        ip.topMargin = Utility.getStatusBarHeight(this)+Utility.dip2px(this,22);
        iv_arrow.setLayoutParams(ip);
        mFragments.clear();
        mFragments.add(new PlayCallFragment());
        mFragments.add(new CallRecordFragment());
        mFragments.add(new ContactsFragment());
        mFragments.add(new PatientInfoQueryFragment());
        mContent = mFragments.get(0);
        commitFragment(R.id.fl_fragment_content,mContent);
        findViewById(R.id.iv_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProPop();
            }
        });
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iv_arrow.getVisibility() == View.VISIBLE){
                    showProPop();
                }
            }
        });
    }

    private void showProPop(){
        ScreenCallRecordPop configPop = new ScreenCallRecordPop(MainActivity.this, configBeanList, true);
        configPop.setBackgroundColor(0x0);
        configPop.setmItemSelectListener(new ScreenCallRecordPop.IItemSelectListener<ConfigBean>() {
            @Override
            public void onSelect(ConfigBean data) {
                ContactsFragment f = (ContactsFragment) mFragments.get(2);
                if(!"全部".equals(data.getProName())){
                    tv_title.setText(data.getProName()+"-"+"通讯录");
                } else {
                    tv_title.setText("通讯录");
                }
                f.selectItem(data);
            }
        });
        int[] los = new int[2];
        tv_title.getLocationOnScreen(los);
        configPop.showPopupWindow(0, los[1] + tv_title.getHeight());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE: //拨打电话
                if (!verifyPermissions(grantResults)) {
                    showMissingPermissionDialog();
                } else {
                    if(!Settings.canDrawOverlays(this)){
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        startService();
                    }
                }
                break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(!Settings.canDrawOverlays(this)){
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    private void startService(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Intent intent = new Intent(this, JobSchedulerService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                this.startService(intent);
            }
        } else {
            Intent intent = new Intent(this, TracePhoneService.class);
            this.startService(intent);
        }
        LogUtils.loge("","startService");
    }

    private void initListener() {
        RadioGroup radioGroup = findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_call:
                        switchContent(mFragments.get(0));
                        tv_title.setText("拨号");
                        iv_arrow.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rb_record:
                        switchContent(mFragments.get(1));
                        tv_title.setText("话单记录");
                        iv_arrow.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rb_contracts:
                        switchContent(mFragments.get(2));
                        tv_title.setText("通讯录");
                        iv_arrow.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_search:
                        switchContent(mFragments.get(3));
                        tv_title.setText("患者信息查询");
                        iv_arrow.setVisibility(View.INVISIBLE);
                        break;

                }
            }
        });
    }


    private void initData() {
        //valueList = mVirtualKeyBoard.getValueList();

        //mFragments.add(new ClaimFragment());
//        mTablayout.setTabData(mTitles,this,R.id.activity_main_contentFrame,mFragments);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            startService();
        }
        OkGo.post(Api.CONFIG_INFO)
                .params("attacheTrue", SpUtil.getString(this, GloableConstant.ATTACHETRUE))
                .execute(new JsonCallback<BaseBean<List<ConfigBean>>>() {
                    @Override
                    public void onSuccess(BaseBean<List<ConfigBean>> listBaseBean, Call call, Response response) {
                        if(listBaseBean.getResult() == 0){
                            configBeanList = listBaseBean.getData();
                        }
                    }
                });
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
                Log.e("debug", "findDeniedPermissions: "+perm );
            }
        }
        return needRequestPermissonList;
    }
    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        Log.e("debug", "verifyPermissions: "+grantResults.length );
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    private void showMissingPermissionDialog() {
        isdialogshow = true;
        LogUtils.loge("showMissingPermissionDialog","showMissingPermissionDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitApp();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                        isdialogshow = false;
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }
    public void exitApp() {

        android.os.Process.killProcess(android.os.Process.myPid());

        System.exit(0);
    }

    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 切换tab页面
     *
     * @param fragment 要显示的fragment
     */
    public void switchContent(Fragment fragment) {
        if (mContent != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_fragment_content, fragment);
            }
            transaction.hide(mContent).show(fragment)
                    .commitAllowingStateLoss();

            mContent = fragment;
        }
    }

    private void commitFragment(int layoutId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
