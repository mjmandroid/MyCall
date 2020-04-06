package com.it.fan.mycall.base;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.it.fan.mycall.R;


@SuppressLint("ValidFragment")
public class BaseDialog extends BaseDialogFragment{
    private final Builder builder;
    private OnDialogClickListener mListener;

    public BaseDialog(Builder builder) {
        super();
        this.builder = builder;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去掉dialog的标题，需要在setContentView()之前
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getDialog().getWindow();
        //去掉dialog默认的padding
        window.getDecorView().setPadding(builder.l, builder.t, builder.r, builder.b);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = builder.dialogWidth;
        if(builder.isSetHeight){
            lp.height = builder.dialogHeight;
        } else {
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        //设置dialog的位置在底部
        lp.gravity = builder.gravityStyle;
        //设置dialog的动画
        lp.windowAnimations = builder.animResource;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable());
        final View view = inflater.inflate(builder.layResuorce, null);

        if (builder.okviewId != 0) {
            View okview = view.findViewById(builder.okviewId);
            okview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onOkClick();
                }
            });
        }

        if (builder.ok2viewId != 0) {
            View ok2view = view.findViewById(builder.ok2viewId);
            ok2view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onOk2Click();
                }
            });
        }

        if (builder.cancelViewId != 0) {
            View cancelView = view.findViewById(builder.cancelViewId);
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        setCancelable(builder.isCancel);
        return view;
    }

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isAdded())
            super.show(manager, tag);
    }

    public interface OnDialogClickListener {

        void onOkClick();

        /**
         * 第二个功能按钮点击事件，没有就不需要实现
         */
        void onOk2Click();

    }

    public static class Builder {

        private int l = 0;
        private int t = 0;
        private int r = 0;
        private int b = 0;

        //dialog默认动画
        private int animResource = R.style.dialog_animation;

        ////dialog的默认位置在底部
        private int gravityStyle = Gravity.BOTTOM;

        //shape_dialog width模式
        private int dialogWidth = WindowManager.LayoutParams.MATCH_PARENT;
        private int dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        //設置布局
        private int layResuorce;

        //只有两个按钮的dialog
        //设置确定按钮
        private int okviewId = 0;

        //拓展有两个功能按钮时
        private int ok2viewId = 0;

        //设置取消按钮
        private int cancelViewId = 0;

        private boolean isCancel = true;
        private boolean isSetHeight = false;

        public BaseDialog build() {
            return new BaseDialog(this);
        }

        public Builder padding(int l, int t, int r, int b) {
            this.l = l;
            this.t = t;
            this.r = r;
            this.b = b;
            return this;
        }

        public Builder animResource(int animResoutce) {
            this.animResource = animResoutce;
            return this;
        }

        public Builder gravityStyle(int gravityStyle) {
            this.gravityStyle = gravityStyle;
            return this;
        }

        /**
         * @param dialogWidth -1 代表 MATCH_PARENT  -2代表 WARP_CONTANT
         * @return
         */
        public Builder dialogWidth(int dialogWidth) {

            this.dialogWidth = dialogWidth;
            return this;
        }

        public Builder dialogHeight(int dialogHeight) {

            this.dialogHeight = dialogHeight;
            return this;
        }

        public Builder layResuorce(int layResuorce) {
            this.layResuorce = layResuorce;
            return this;
        }

        /**
         * @param viewId 确认点击view的id
         * @return
         */
        public Builder okViewClickEvent(int viewId) {
            this.okviewId = viewId;
            return this;
        }

        /**
         * @param viewId 第二个功能按钮点击view的id
         * @return
         */
        public Builder ok2ViewClickEvent(int viewId) {
            this.ok2viewId = viewId;
            return this;
        }

        /**
         * @param viewId 取消按钮的id
         * @return
         */
        public Builder cancelViewClickEvent(int viewId) {
            this.cancelViewId = viewId;
            return this;
        }

        /**
         * @param isCancel 点击空白处是否消失 true 表示消失
         * @return
         */
        public Builder outSideCancel(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }

        public Builder setDialogHeight(boolean isSetHeight) {
            this.isSetHeight = isSetHeight;
            return this;
        }
    }
}
