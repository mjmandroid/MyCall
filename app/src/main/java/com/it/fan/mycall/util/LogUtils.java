package com.it.fan.mycall.util;

import android.util.Log;

public class LogUtils {
    private static final int logLevel = 0;
    private static int currentLevel = 1;
    private static String TAG = "debug";

    public static void loge(String tag,String content){
        if(currentLevel > logLevel){
            Log.e(TAG, tag+": "+content);
        }
    }

    public static void logw(String tag,String content){
        if(currentLevel > logLevel){
            Log.w(TAG, tag+": "+content);
        }
    }

    public static void logi(String tag,String content){
        if(currentLevel > logLevel){
            Log.i(TAG, tag+": "+content);
        }
    }
}
