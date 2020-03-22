package com.it.fan.mycall.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.it.fan.mycall.util.LogUtils;

public class NotificationClickReceiver extends BroadcastReceiver {

    public final static String ACTION_CLICK_NOTIFICATION = "com.xunjoy.keeplive.receiver.ACTION_CLICK_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_CLICK_NOTIFICATION.equals(intent.getAction())) {
            LogUtils.loge("receiver","aaaa");
        }
    }
}
