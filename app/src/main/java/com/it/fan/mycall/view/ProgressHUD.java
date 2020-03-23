package com.it.fan.mycall.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.fan.mycall.R;
import com.it.fan.mycall.util.Utility;

/**
 * loading
 *
 * @author longluliu
 * @ClassName: ProgressHUD
 * @Description: TODO
 * @date 2014-7-22 上午10:57:35
 */
public class ProgressHUD extends Dialog {
    private Context contexts;

    public ProgressHUD(Context context) {
        super(context);
        contexts = context;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        startAnimation(ContextCompat.getDrawable(contexts, R.drawable.lib_progress_loading_anim));
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
        contexts = context;
    }

    public static ProgressHUD show(Context context, int message) {
        String msg = context.getString(message);
        return show(context, msg);
    }

    public static ProgressHUD show(Context context, String message) {
        return show(context, message, true, true, null);
    }

    public static ProgressHUD show(Context context, String message, boolean cancelable) {
        return show(context, message, true, cancelable, null);
    }

    private static ProgressHUD show(Context context, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
        ProgressHUD dialoghud = new ProgressHUD(context, R.style.Lib_ProgressHUD);
        dialoghud.setTitle("");
        View view = LayoutInflater.from(context).inflate(R.layout.lib_view_progress_hud, null);
        Utility.setAlphaBg(view, 127);
        dialoghud.setContentView(view);
        if (message == null || message.length() == 0) {
            dialoghud.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialoghud.findViewById(R.id.message);
            txt.setText(message);
        }
        dialoghud.setOnCancelListener(cancelListener);
        if (dialoghud.getWindow() != null && dialoghud.getWindow().getAttributes() != null) {
            dialoghud.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialoghud.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dialoghud.getWindow().setAttributes(lp);
        }
        dialoghud.setCancelable(cancelable);
        dialoghud.show();
        return dialoghud;
    }

    public static ProgressHUD show(Context context, String title, String message) {
        return show(context, title, message, true, true, null);
    }

    private static ProgressHUD show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable,
                                    OnCancelListener cancelListener) {
        ProgressHUD dialoghud = new ProgressHUD(context, R.style.Lib_ProgressHUD);
        dialoghud.setTitle("");
        View view = LayoutInflater.from(context).inflate(R.layout.lib_view_progress_hud_has_title, null);
        Utility.setAlphaBg(view, 127);
        dialoghud.setContentView(view);
        if (title == null || title.length() == 0) {
            dialoghud.findViewById(R.id.tvTitle).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialoghud.findViewById(R.id.tvTitle);
            txt.setText(title);
        }
        if (message == null || message.length() == 0) {
            dialoghud.findViewById(R.id.tvMessage).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialoghud.findViewById(R.id.tvMessage);
            txt.setText(message);
        }

        dialoghud.setOnCancelListener(cancelListener);
        if (dialoghud.getWindow() != null && dialoghud.getWindow().getAttributes() != null) {
            dialoghud.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialoghud.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dialoghud.getWindow().setAttributes(lp);
        }
        dialoghud.setCancelable(cancelable);
        dialoghud.show();
        return dialoghud;
    }

    private void startAnimation(Drawable imageDrawable) {
        // Set Drawable
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        if (imageView != null) {
            imageView.setImageDrawable(imageDrawable);
            //            mUseIntrinsicAnimation = (imageDrawable instanceof AnimationDrawable);
            if (imageDrawable instanceof AnimationDrawable) {
                AnimationDrawable spinner = (AnimationDrawable) imageDrawable;
                spinner.start();
            }
        }
    }

}
