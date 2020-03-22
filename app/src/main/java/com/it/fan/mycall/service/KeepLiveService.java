package com.it.fan.mycall.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import com.it.fan.mycall.R;
import com.it.fan.mycall.util.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

public class KeepLiveService extends Service {
    private static final int sHashCode = 1;

    public KeepLiveService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "com.xunjoy.zhipuzi.deliveryman".hashCode() + "";
            Notification notification = new Notification.Builder(getApplicationContext(), id).build();
            startForeground(15, notification); //这个id不要和应用内的其他同志id一样，不行就写 int.maxValue()        //context.startForeground(SERVICE_ID, builder.getNotification());
        }

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.loge("onStartCommand","onStartCommand");
        return onStart(intent, flags, startId);
    }



    /**
     * 1.防止重复启动，可以任意调用startService(Intent i);
     * 2.利用漏洞启动前台服务而不显示通知;
     * 3.在子线程中运行定时任务，处理了运行前检查和销毁时保存的问题;
     * 4.启动守护服务.
     */
    private int onStart(Intent intent, int flags, int startId) {
        //启动前台服务而不显示通知的漏洞已在 API Level 25 修复，大快人心！
        try {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                int icon = R.mipmap.ic_launcher;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setSmallIcon(icon);
                    builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
                    builder.setContentText("智铺子，智慧门店服务商");
                    builder.setContentTitle("智铺子配送员");
                    Notification n = builder.getNotification();
                    startForeground(sHashCode, n);
                }
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN
                        && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setSmallIcon(icon);
                    builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
                    builder.setContentText("智铺子，智慧门店服务商");
                    builder.setContentTitle("智铺子配送员");
                    Notification n = builder.build();
                    startForeground(sHashCode, n);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setSmallIcon(icon);
                    builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
                    builder.setContentText("智铺子，智慧门店服务商");
                    builder.setContentTitle("智铺子配送员");
                    Notification n = builder.build();
                    startForeground(sHashCode, n);
                }
            }
        } catch (Exception e) {
        }
        //利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            try {
//                startService(new Intent(getApplication(), WorkNotificationService.class));
            } catch (Exception e) {

            }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        onStart(intent, 0, 0);
        return null;
    }



    public static class WorkNotificationService extends Service {
        /**
         * 利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    int icon = R.mipmap.ic_launcher;
                    int aphla_icon = R.mipmap.ic_launcher;
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                        Notification.Builder builder = new Notification.Builder(this);
                        builder.setSmallIcon(icon);
                        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
                        builder.setContentText("智铺子，智慧门店服务商");
                        builder.setContentTitle("智铺子配送员");
                        Notification n = builder.getNotification();
                        startForeground(sHashCode, n);
                    }
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN
                            && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        Notification.Builder builder = new Notification.Builder(this);
                        builder.setSmallIcon(icon);
                        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
                        builder.setContentText("智铺子，智慧门店服务商");
                        builder.setContentTitle("智铺子配送员");
                        Notification n = builder.build();
                        startForeground(sHashCode, n);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Notification.Builder builder = new Notification.Builder(this);
                        builder.setSmallIcon(aphla_icon);
                        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher));
                        builder.setContentText("智铺子，智慧门店服务商");
                        builder.setContentTitle("智铺子配送员");
                        Notification n = builder.build();
                        startForeground(sHashCode, n);
                    }

                }

            } catch (Exception e) {

            }
            stopSelf();
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }


}
