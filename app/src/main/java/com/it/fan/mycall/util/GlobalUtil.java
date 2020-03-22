package com.it.fan.mycall.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.it.fan.mycall.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GlobalUtil {
    private static Toast sToast = null;
    private static String mUUIDString;


    public static void shortToast(Context context, int resId) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.view_toast_bg, null);
        TextView textView = (TextView) contentView.findViewById(R.id.tost_tv);

        if (sToast == null) {
            // Rect r = DeviceUtil.getScreenRect(context);
            sToast = new Toast(context);
            textView.setText(context.getText(resId));
            sToast.setView(contentView);
            sToast.setDuration(Toast.LENGTH_LONG);
            // sToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
            // r.bottom / 5);
            sToast.show();
        } else {
            textView.setText(context.getText(resId));
            sToast.setView(contentView);
            sToast.setDuration(Toast.LENGTH_LONG);
            sToast.show();
        }
    }


    public static void shortToast(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.view_toast_bg, null);
        TextView textView = (TextView) contentView.findViewById(R.id.tost_tv);

        if (sToast == null) {
            // Rect r = DeviceUtil.getScreenRect(context);
            sToast = new Toast(context);
            textView.setText(msg);
            sToast.setView(contentView);
            sToast.setDuration(Toast.LENGTH_SHORT);
            // sToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
            // r.bottom / 5);
            sToast.show();
        } else {
            textView.setText(msg);
            sToast.setView(contentView);
            sToast.setDuration(Toast.LENGTH_SHORT);
            sToast.show();
        }
    }


//    public static String getUUIDString(Context context) {
//        if (mUUIDString != null) {
//            return mUUIDString;
//        }
//
//        String uuidString = PreferenceUtil.getString(context,
//                ConstantsValue.ANDROID_UUID, null);
//        if (uuidString != null) {
//            mUUIDString = uuidString;
//        } else {
//            UUID uuid = UUID.randomUUID();
//
//            PreferenceUtil.putString(context, ConstantsValue.ANDROID_UUID,
//                    uuid.toString());
//            mUUIDString = uuid.toString();
//        }
//
//        return mUUIDString;
//
//    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;

            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取versioncode
     */
    public static int getViersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取Android系统版本号
     *
     * @return
     */
    public static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

    public static void sendBroadcast(Context activity, String action,
                                     String contitle, String con) {
        Intent intent = new Intent();
        intent.setAction(action);

        // 要发送的内容
        intent.putExtra(contitle, con);

        // 发送 一个无序广播
        activity.sendBroadcast(intent);
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));
        return re_StrTime;
    }

    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
            String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
                valueLength += 2;
            } else {
				/* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }


    public static void callPhone(Context context, String number) {
        //用intent启动拨打电话
        if (!number.contains("tel:")) {
            number = "tel:" + number;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    public static void openUrlByBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        String realUrl;
        if (!url.contains("http://")) {
            realUrl = "http://" + url;
        } else {
            realUrl = url;
        }
        intent.setData(Uri.parse(realUrl));
        context.startActivity(intent);
    }



}
