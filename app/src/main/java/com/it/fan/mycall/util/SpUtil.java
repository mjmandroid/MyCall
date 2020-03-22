package com.it.fan.mycall.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by Administrator on 2016/12/19.
 */
public class SpUtil {
    private static final String MYPRO = "myplay";
    public static void SaveString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(MYPRO, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(MYPRO, Activity.MODE_PRIVATE);
        String myContent = sp.getString(key, "");
        return myContent;
    }

    public static void SaveBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences(MYPRO, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(MYPRO, Activity.MODE_PRIVATE);
        boolean myContent = sp.getBoolean(key, defaultValue);
        return myContent;
    }

    public static void SaveInt(Context mcontext, String key, int value) {
        SharedPreferences sp = mcontext.getSharedPreferences(MYPRO, Activity.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences(MYPRO, Activity.MODE_PRIVATE);
        int myContent = sp.getInt(key,0);
        return myContent;
    }
}
