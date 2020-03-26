package com.it.fan.mycall.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class BaseDialogFragment extends DialogFragment implements View.OnTouchListener {
    private boolean mCancelable = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        boolean isShow = this.getShowsDialog();
        this.setShowsDialog(false);
        super.onActivityCreated(savedInstanceState);
        this.setShowsDialog(isShow);

        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new IllegalStateException(
                        "DialogFragment can not be attached to a container view");
            }
            this.getDialog().setContentView(view);
        }
        final Activity activity = getActivity();
        if (activity != null) {
            this.getDialog().setOwnerActivity(activity);
        }
        this.getDialog().setCancelable(false);
        this.getDialog().getWindow().getDecorView().setOnTouchListener(this);
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        if (savedInstanceState != null) {
            Bundle dialogState = savedInstanceState.getBundle("android:savedDialogState");
            if (dialogState != null) {
                this.getDialog().onRestoreInstanceState(dialogState);
            }
        }
    }

    public void setCancelable(boolean mCancelable) {
        this.mCancelable = mCancelable;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mCancelable && getDialog().isShowing()) {
            dismiss();
            return true;
        }
        return false;
    }
}
