package com.it.fan.mycall.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.it.fan.mycall.R;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.RingBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.receiver.NotificationClickReceiver;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.LogUtils;
import com.it.fan.mycall.util.NotificationUtils;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.view.MyApp;
import com.lzy.okgo.OkGo;

import okhttp3.Call;
import okhttp3.Response;

public class TracePhoneService extends Service {

    private int loopCount = 0;
    private static final int MAX_SIZE = 5;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = getPackageName().hashCode() + "";
            Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
            intent.setAction(NotificationClickReceiver.ACTION_CLICK_NOTIFICATION);
            Notification notification = NotificationUtils.createNotification(this,"mycall","正在后台运行中", R.drawable.login_logo,
                    intent);
            startForeground(1, notification);
//            stopForeground(true);
        }
//        String id = getPackageName().hashCode() + "";
//        Notification notification = new NotificationCompat.Builder(this, id).build();
//        startForeground(100, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("debug", "onStartCommand:CALL_STATE_IDLE " );
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://当前状态为挂断
                        Log.e("debug", "onCallStateChanged:CALL_STATE_IDLE "+phoneNumber );
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://接听或者拨打
                        Log.e("debug", "onCallStateChanged:CALL_STATE_OFFHOOK "+phoneNumber );
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://当前状态为响铃
                        loopCount = 0;
                        Log.e("debug", "onCallStateChanged:CALL_STATE_RINGING "+phoneNumber );
                        searchPhoneInfo(phoneNumber);
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);

        return START_STICKY;
    }

    private void searchPhoneInfo(final String phoneNumber) {
        loopCount++;
        if (loopCount < MAX_SIZE) {
            OkGo.post(Api.RING_PHONE_MESSAGE)
                    .params("attacheTrue", SpUtil.getString(MyApp.getInstance(), GloableConstant.ATTACHETRUE))
                    .params("callerVitrual", phoneNumber)
                    .execute(new JsonCallback<BaseBean<RingBean>>() {
                        @Override
                        public void onSuccess(BaseBean<RingBean> ringBeanBaseBean, Call call, Response response) {
                            RingBean ringBean = ringBeanBaseBean.getData();
                            if (ringBeanBaseBean.getResult() == 0) {
                                StringBuilder showContent = new StringBuilder();
                                showContent.append("【")
                                        .append(ringBean.getProName())
                                        .append("】")
                                        .append(ringBean.getUserHospital())
                                        .append("-")
                                        .append(ringBean.getUserName())
                                        .append("-")
                                        .append(ringBean.getUserLabel())
                                        .append("-")
                                        .append(ringBean.getUserRemark())
                                        .append("-")
                                        .append("正在拨打您的电话，请注意接听~");
                                sendNofity(showContent.toString());
                            } else if (ringBeanBaseBean.getResult() == 2) {
                                StringBuilder showContent = new StringBuilder();
                                showContent.append("【")
                                        .append(ringBean.getProName())
                                        .append("】")
                                        .append(ringBean.getVitrualPhone())
                                        .append("-")
                                        .append("正在拨打您的电话，请注意接听~");
                                sendNofity(showContent.toString());
                            } else {
                                searchPhoneInfo(phoneNumber);
                            }
                        }
                    });
        }

    }

    private void sendNofity(String content) {
        NotificationUtils.sendPhoneNotification(getApplicationContext(),
                "",
                content,
                R.drawable.login_logo,
                null);
    }
}
