package com.it.fan.mycall.view;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.it.fan.mycall.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by fan on 2019/6/7.
 */

public class VirtualKeyBoardPop extends BasePopupWindow {

    private final VirtualKeyboardView mVirtualKeyBoard;

    public VirtualKeyBoardPop(Context context) {
        super(context);

        mVirtualKeyBoard = findViewById(R.id.pop_virtual_keyboard_View);

        initListener();
    }

    private void initListener() {
        mVirtualKeyBoard.setOnVirtualKeyBoardListener(new VirtualKeyboardView.OnVirtualKeyBoardListener() {
            @Override
            public void onBack() {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.pop_virtual_keyboard);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationUtils.loadAnimation(getContext(),R.anim.pop_in);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationUtils.loadAnimation(getContext(),R.anim.pop_out);
    }

}
