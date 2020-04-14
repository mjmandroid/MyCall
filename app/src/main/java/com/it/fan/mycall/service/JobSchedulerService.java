package com.it.fan.mycall.service;

import android.app.Notification;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.it.fan.mycall.R;
import com.it.fan.mycall.receiver.NotificationClickReceiver;
import com.it.fan.mycall.util.NotificationUtils;
import com.it.fan.mycall.util.ServiceUtils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    private static final int JOB_SERVICE_ID = 11000;

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = getPackageName().hashCode() + "";
            Intent intent = new Intent(getApplicationContext(), NotificationClickReceiver.class);
            intent.setAction(NotificationClickReceiver.ACTION_CLICK_NOTIFICATION);
            Notification notification = NotificationUtils.createNotification(this,"mycall","正在后台运行中",R.drawable.login_logo,
                    intent);
            startForeground(1, notification);
//            stopForeground(true);
        }
        startService(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(JOB_SERVICE_ID, new ComponentName(getPackageName(), JobSchedulerService.class.getName()))
                    .setPersisted(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //执行的最小延迟时间
                builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
                //执行的最长延时时间
                builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
                //线性重试方案
                builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS, JobInfo.BACKOFF_POLICY_LINEAR);
            } else {
                builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
                builder.setRequiresDeviceIdle(true);
            }
            //需要网络
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            // 当插入充电器，执行该任务
            builder.setRequiresCharging(true);
            jobScheduler.schedule(builder.build());
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    private void startService(Context context) {
        Intent intent = new Intent(this, TracePhoneService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        if(!ServiceUtils.isServiceRunning(getApplicationContext(),TracePhoneService.class.getName())){
            startService(this);
        }
        Log.e("debug", "onStartJob: " );
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if(!ServiceUtils.isServiceRunning(getApplicationContext(),TracePhoneService.class.getName())){
            startService(this);
        }
        Log.e("debug", "onStopJob: " );
        return false;
    }
}
