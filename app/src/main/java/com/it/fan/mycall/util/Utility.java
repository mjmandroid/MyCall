package com.it.fan.mycall.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.it.fan.mycall.view.MyApp;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Administrator on 2017/3/22.
 */
public class Utility {
    /**
     * 改变通知栏颜色
     */
    public static void setActionBar(Context context,int colorRes){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(context, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager((Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(colorRes);
    }

    @TargetApi(19)
    public static void setTranslucentStatus(Context context, boolean on) {
        Window win = ((Activity)context).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void setAlphaBg(View view, int alpha) {
        if (view != null && view.getBackground() != null) {
            /*0-255*/
            view.getBackground().setAlpha(alpha);
        }
    }

    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dip2px(Context context, double dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void showToast(String str){
        Toast.makeText(MyApp.getInstance(),str,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String str){
        Toast.makeText(MyApp.getInstance(),str,Toast.LENGTH_LONG).show();
    }
}
