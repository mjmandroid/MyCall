package com.it.fan.mycall.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.NotificationCompat;

import com.it.fan.mycall.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fan on 2019/6/7.
 */

public class BrageUtil {

    /**
     * 向小米手机发送未读消息数广播
     * @param count
     */ public static void sendToXiaoMi(Context context, int count) {
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("标题").setContentText("消息正文").setSmallIcon(R.drawable.login_logo);
        Notification notification = builder.build();
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNotificationManager.notify(0, notification);

    }


    /**
     * Retrieve launcher activity name of the application from the context
     *
     * @param context The context of the application package.
     * @return launcher activity name of this application. From the
     *         "android:name" attribute.
     */ private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER); // All Application must have 1 Activity at least. // Launcher activity must be found!
         ResolveInfo info = packageManager .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY); // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER // if there is no Activity which has filtered by CATEGORY_DEFAULT
         if (info == null) {
             info = packageManager.resolveActivity(intent, 0);
         }

         return info.activityInfo.name;
     }


}
