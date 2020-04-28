package com.rokid.glass.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rokid.glass.ui.R;
import com.rokid.glass.ui.button.GlassButton;

/**
 * @author jian.yang
 * @date 2019/3/6
 */

public class GlassDialog extends Dialog {
    private GlassDialogBuilder mBuilder;

    GlassDialog(Context context, GlassDialogBuilder builder) {
        this(context, R.style.GlassDialogStyle, builder);
    }

    GlassDialog(Context context, int themeResId, GlassDialogBuilder builder) {
        super(context, themeResId);
        this.mBuilder = builder;
        init();
    }

    private void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    private void initDialog() {
        Window window = getWindow();
        if (window == null) {
            return;
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(params);

        if (null != mBuilder && mBuilder.getAnimationStyle() != 0) {
            window.setWindowAnimations(mBuilder.getAnimationStyle());
        } else {
            window.setWindowAnimations(R.style.GlassDialogWindowAnimation);
        }

        if (null != mBuilder && mBuilder.isApplicationDialog()) {
            if (!(getContext() instanceof Activity)) { //
                if (Build.VERSION.SDK_INT >= 26) {//8.0
                    window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                } else {
                    window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                }
            }
        }
    }

    @Override
    public void show() {
        super.show();
        if (null != mBuilder) {
            mBuilder.dialogShow();
        }
    }

    /**
     * common dialog builder
     * title
     * content
     */
    public static class CommonDialogBuilder extends GlassDialogBuilder<CommonDialogBuilder> {
        private TextView mDialogTitleTv;
        private TextView mDialogContentTv;
        private GlassButton mConfirmBtn;
        private GlassButton mCancelBtn;

        private String mTitle;
        protected String mConfirmText;
        protected String mCancelText;
        protected String mContent;

        protected GlassDialogListener mConfirmListener;
        protected GlassDialogListener mCancelListener;

        public CommonDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected void init() {

        }

        @Override
        protected void onCreateContent(Context context, ViewGroup parent, GlassDialog dialog) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_glass_common_dialog, parent, false);
            mDialogTitleTv = view.findViewById(R.id.dialog_title);
            mDialogContentTv = view.findViewById(R.id.dialog_content);
            mConfirmBtn = view.findViewById(R.id.dialog_confirm);
            mCancelBtn = view.findViewById(R.id.dialog_cancel);

            mDialogTitleTv.setText(mTitle);
            if (!TextUtils.isEmpty(mConfirmText)) {
                mConfirmBtn.setText(mConfirmText);
            }

            if (!TextUtils.isEmpty(mContent)) {
                mDialogContentTv.setVisibility(View.VISIBLE);
                mDialogContentTv.setText(mContent);
            } else {
                mDialogContentTv.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(mCancelText)) {
                mCancelBtn.setVisibility(View.VISIBLE);
                mCancelBtn.setText(mCancelText);

                mCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (null != mCancelListener) {
                            mCancelListener.onClick(v);
                        }
                    }
                });

            } else {
                mCancelBtn.setVisibility(View.GONE);
            }

            mConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (null != mConfirmListener) {
                        mConfirmListener.onClick(v);
                    }
                }
            });

            mGlassDialog.setCancelable(true);
            parent.addView(view);
        }

        public CommonDialogBuilder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public CommonDialogBuilder setConfirmText(String confirmText) {
            this.mConfirmText = confirmText;
            return this;
        }

        public CommonDialogBuilder setCancelText(String cancelText) {
            this.mCancelText = cancelText;
            return this;
        }

        public CommonDialogBuilder setContent(String content) {
            this.mContent = content;
            return this;
        }

        public CommonDialogBuilder setConfirmListener(GlassDialogListener confirmListener) {
            this.mConfirmListener = confirmListener;
            return this;
        }

        public CommonDialogBuilder setCancelListener(GlassDialogListener cancelListener) {
            this.mCancelListener = cancelListener;
            return this;
        }
    }
}
