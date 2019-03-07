package com.rokid.glass.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokid.glass.ui.R;

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
        setCancelable(true);
        setCanceledOnTouchOutside(true);
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
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(params);
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    @Override
    public void show() {
        super.show();
        if (null != mBuilder) {
            mBuilder.dialogShow();
        }
    }

    /**
     * Notification Dialog
     */
    public static class NotificationDialogBuilder extends GlassDialogBuilder<NotificationDialogBuilder> {
        private TextView mMessageTv;
        private ImageView mIconResIv;
        private int mDuration = 3000;
        private Handler mHandler;

        private String mMessage;
        private int mIconRes;

        public NotificationDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected void init() {
            mHandler = new Handler();
        }

        public NotificationDialogBuilder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public NotificationDialogBuilder setIconRes(int iconRes) {
            this.mIconRes = iconRes;
            return this;
        }

        public NotificationDialogBuilder setDuration(int duration) {
            this.mDuration = duration;
            return this;
        }

        @Override
        protected void onCreateContent(Context context, ViewGroup parent, GlassDialog dialog) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_notification_dialog, parent, false);
            mTitleTv = view.findViewById(R.id.dialog_notification_title);
            mMessageTv = view.findViewById(R.id.dialog_notification_message);
            mIconResIv = view.findViewById(R.id.dialog_notification_icon);

            if (mIconRes != 0) {
                mIconResIv.setImageResource(mIconRes);
            }

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
            mTitleTv.setText(mTitle);
            mMessageTv.setText(mMessage);
            parent.addView(view);
        }

        @Override
        protected void dialogShow() {
            super.dialogShow();
            if (mDuration > 0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mGlassDialog && mGlassDialog.isShowing()) {
                            mGlassDialog.dismiss();
                        }
                    }
                }, mDuration);
            }
        }
    }
}
