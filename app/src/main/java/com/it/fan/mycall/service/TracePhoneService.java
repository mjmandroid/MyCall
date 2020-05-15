package com.it.fan.mycall.service;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.it.fan.mycall.R;
import com.it.fan.mycall.activity.LockedScreenActivity;
import com.it.fan.mycall.bean.BaseBean;
import com.it.fan.mycall.bean.ConfigBean;
import com.it.fan.mycall.bean.RingBean;
import com.it.fan.mycall.gloable.GloableConstant;
import com.it.fan.mycall.receiver.NotificationClickReceiver;
import com.it.fan.mycall.util.Api;
import com.it.fan.mycall.util.JsonCallback;
import com.it.fan.mycall.util.LogUtils;
import com.it.fan.mycall.util.NotificationUtils;
import com.it.fan.mycall.util.SpUtil;
import com.it.fan.mycall.util.Utility;
import com.it.fan.mycall.view.MyApp;
import com.lzy.okgo.OkGo;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.OkGo.getContext;

public class TracePhoneService extends Service {

    private int loopCount = 0;
    private static final int MAX_SIZE = 5;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            sendNofity("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            Log.e("debug", "handleMessage: "+"aaaaaaaaaaaaaaaaaaaaa" );
        }
    };
    private PhoneReceiver phoneReceiver;


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
//        handler.sendEmptyMessageDelayed(1,1000*10);
//        String id = getPackageName().hashCode() + "";
//        Notification notification = new NotificationCompat.Builder(this, id).build();
//        startForeground(100, notification);
        Log.e("debug", "onCreate: " );
        phoneReceiver = new PhoneReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(phoneReceiver,intentFilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("debug", "onStartCommand:CALL_STATE_IDLE " );

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
                                        .append(ringBean.getUserName())
                                        .append("-")
                                        .append(ringBean.getUserLabel())
                                        .append("-")
                                        .append(ringBean.getUserRemark())
                                        .append("-")
                                        .append("正在拨打您的电话，请注意接听~");
                                Log.e("debug", "onSuccess: "+ ringBean.toString());
//                                Toast.makeText(getApplicationContext(),"查询成功 result="+ringBeanBaseBean.getResult(),Toast.LENGTH_LONG).show();
                                sendNofity(showContent.toString());
                            } else if (ringBeanBaseBean.getResult() == 2) {
                                StringBuilder showContent = new StringBuilder();
                                showContent.append("【")
                                        .append(ringBean.getProName())
                                        .append("】")
                                        .append(ringBean.getUserPhone())
                                        .append("-")
                                        .append("正在拨打您的电话，请注意接听~");
                                sendNofity(showContent.toString());
//                                Toast.makeText(getApplicationContext(),"查询成功 result="+ringBeanBaseBean.getResult(),Toast.LENGTH_LONG).show();
                                Log.e("debug", "onSuccess: "+ ringBean.toString());
                            } else {
                                Log.e("debug", "onSuccess: 查询失败");
//                                Toast.makeText(getApplicationContext(),"查询失败 result="+ringBeanBaseBean.getResult(),Toast.LENGTH_LONG).show();
                                searchPhoneInfo(phoneNumber);
//                                sendNofity(ringBeanBaseBean.getMsg());
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            Log.e("debug", "onError: 查询失败");
//                            Toast.makeText(getApplicationContext(),"网络异常 result="+e.toString(),Toast.LENGTH_LONG).show();
                            searchPhoneInfo(phoneNumber);
                        }
                    });
        }

    }

    private void sendNofity(String content) {
//        Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
//        NotificationUtils.sendPhoneNotification(getApplicationContext(),
//                "",
//                content,
//                R.drawable.login_logo,
//                intent);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View notificationView = inflater
                .inflate(R.layout.remote_view_phone_ring_layout, null);

        final WindowManager mWindowManager = (WindowManager) this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

        wmParams.gravity = Gravity.LEFT|Gravity.TOP;

        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if(isScreenLock(this)){
            wmParams.y = Utility.dip2px(this,50);
        } else {
            wmParams.y = Utility.dip2px(this,150);
        }
        wmParams.windowAnimations = R.style.notification_animation;
        try {
            mWindowManager.addView(notificationView, wmParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView tv_content = notificationView.findViewById(R.id.content);
        tv_content.setText(content);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(notificationView);
            }
        },20*1000);
    }

    /**
     * @param context
     * @return 是否锁屏
     */
    private boolean isScreenLock(Context context){
        //屏锁管理器
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");

        return km.isKeyguardLocked();
    }

    public  boolean wakeUpAndUnlock(Context context){
        //屏锁管理器
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        if(!km.isKeyguardLocked()){
            return false;
        }
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(phoneReceiver);
    }

    private class PhoneReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!Intent.ACTION_NEW_OUTGOING_CALL.equals(action)){
                TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                mTelephonyManager.listen(new PhoneStateListener() {
                    @Override
                    public void onCallStateChanged(int state, String phoneNumber) {
                        switch (state) {
                            case TelephonyManager.CALL_STATE_IDLE://当前状态为挂断
                                Log.e("debug", "onCallStateChanged:CALL_STATE_IDLE "+phoneNumber );
                                break;
                            case TelephonyManager.CALL_STATE_OFFHOOK://接听或者拨打
                                Log.e("debug", "onCallStateChanged:CALL_STATE_IDLE "+phoneNumber );
                                Log.e("debug", "onCallStateChanged:CALL_STATE_OFFHOOK "+phoneNumber );
                                break;
                            case TelephonyManager.CALL_STATE_RINGING://当前状态为响铃
                                loopCount = 0;
//                                Toast.makeText(getApplicationContext(),"来电"+phoneNumber,Toast.LENGTH_SHORT).show();
                                Log.e("debug", "onCallStateChanged:CALL_STATE_RINGING "+phoneNumber );
                                searchPhoneInfo(phoneNumber);

                                break;
                        }
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE);
            }
        }
    }
}
