package com.rokid.glass.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokid.glass.ui.R;
import com.rokid.glass.ui.util.Utils;

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

//        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    }

    @Override
    public void show() {
        super.show();
        if (null != mBuilder) {
            mBuilder.dialogShow();
        }
    }

    private static class MessageDialogBuilder<T extends GlassDialogBuilder> extends GlassDialogBuilder<T> {
        public MessageDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected void init() {

        }

        @Override
        protected void onCreateContent(Context context, ViewGroup parent, GlassDialog dialog) {

        }

        @Override
        protected void onAfter(Context context, ViewGroup parent, GlassDialog dialog) {
            super.onAfter(context, parent, dialog);
            ViewGroup.LayoutParams params = parent.getLayoutParams();
            params.height = Utils.getScreenHeight(context);
            parent.setLayoutParams(params);
        }
    }

    /**
     * pure voice
     */
    public static class SimpleVoiceDialogBuilder extends MessageDialogBuilder<SimpleVoiceDialogBuilder> {
        private ViewGroup mVoiceLayout;
        private Button mConfirmBtn;
        private Button mCancelBtn;
        private View mCustomConfirmView;
        private String mConfirmText;
        private String mCancelText;

        private GlassDialogListener mConfirmListener;
        private GlassDialogListener mCancelListener;

        public SimpleVoiceDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected void onCreateContent(Context context, ViewGroup parent, final GlassDialog dialog) {
            super.onCreateContent(context, parent, dialog);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_simple_voice_dialog, parent, false);
            mTitleTv = view.findViewById(R.id.simple_voice_title);
            mConfirmBtn = view.findViewById(R.id.confirm_btn);
            mCancelBtn = view.findViewById(R.id.cancel_btn);
            mVoiceLayout = view.findViewById(R.id.voice_layout);

            mTitleTv.setText(mTitle);

            if (!TextUtils.isEmpty(mConfirmText)) {
                mConfirmBtn.setText(mConfirmText);
            }

            if (!TextUtils.isEmpty(mCancelText)) {
                mCancelBtn.setText(mCancelText);
            }

            mConfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    disimiss();
                    if (null != mConfirmListener) {
                        mConfirmListener.onClick(v);
                    }
                }
            });

            mCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disimiss();
                    if (null != mCancelListener) {
                        mCancelListener.onClick(v);
                    }
                }
            });

            parent.addView(view);
        }

        public SimpleVoiceDialogBuilder setConfirmText(String confirmText) {
            this.mConfirmText = confirmText;
            return this;
        }

        public SimpleVoiceDialogBuilder setCancelText(String cancelText) {
            this.mCancelText = cancelText;
            return this;
        }


        public SimpleVoiceDialogBuilder setConfirmListener(GlassDialogListener confirmListener) {
            this.mConfirmListener = confirmListener;
            return this;
        }

        public SimpleVoiceDialogBuilder setCancelListener(GlassDialogListener cancelListener) {
            this.mCancelListener = cancelListener;
            return this;
        }

        //dynamic content
        public void dynamicConfirmText(final String confirmText) {
            if (mGlassDialog.isShowing()) {
                mConfirmBtn.setEnabled(true);
                mConfirmBtn.setText(confirmText);

                if (null != mCustomConfirmView && null != mCustomConfirmView.getParent()) {
                    ((ViewGroup) mCustomConfirmView.getParent()).removeAllViews();
                }
            }
        }

        public void dynamicCancelText(final String cancelText) {
            if (mGlassDialog.isShowing()) {
                mCancelBtn.setText(cancelText);
            }
        }

        public void dynamicCustomConfirmView(View customConfirmView) {
            this.mConfirmBtn.setEnabled(false);
            this.mConfirmBtn.setText(null);
            this.mCustomConfirmView = customConfirmView;
            this.mVoiceLayout.removeAllViews();
            if (null != mCustomConfirmView.getParent()) {
                ((ViewGroup) mCustomConfirmView.getParent()).removeAllViews();
            }
            this.mVoiceLayout.addView(mCustomConfirmView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
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
                        disimiss();
                    }
                }, mDuration);
            }
        }
    }
}
