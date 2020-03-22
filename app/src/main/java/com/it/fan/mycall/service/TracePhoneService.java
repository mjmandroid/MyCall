package com.it.fan.mycall.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.it.fan.mycall.R;
import com.it.fan.mycall.receiver.NotificationClickReceiver;
import com.it.fan.mycall.util.LogUtils;
import com.it.fan.mycall.util.NotificationUtils;

public class TracePhoneService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        String id = "com.xunjoy.zhipuzi.deliveryman".hashCode() + "";
        Notification notification = new NotificationCompat.Builder(this, id).build();
        startForeground(100, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TelephonyManager mTelephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                switch (state){
                    case TelephonyManager.CALL_STATE_IDLE://当前状态为挂断

                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://接听或者拨打

                        break;
                    case TelephonyManager.CALL_STATE_RINGING://当前状态为响铃

                        break;
                }
            }
        },PhoneStateListener.LISTEN_CALL_STATE);

        return START_STICKY;
    }

    private void sendNofity() {
        LogUtils.loge("nofify","aaaaaaaaaaaaaaaaaaaaaaaaa");
        Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
        intent.setAction(NotificationClickReceiver.ACTION_CLICK_NOTIFICATION);
        NotificationUtils.sendNotification(getApplicationContext(),
                "assa",
                "sdsdsdsdsdsdsdsdsd00",
                R.mipmap.ic_launcher,
                intent);
    }
}
