package com.rokid.glass.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokid.glass.ui.R;
import com.rokid.glass.ui.imageview.RoundCornerImageView;
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
     *
     */
    public static class CustomerSimpleMessageBuilder extends CustomerMessageDialogBuilder<CustomerSimpleMessageBuilder> {
        private int mLayoutWidth;
        private int mLayoutHeight;
        private int mLayoutContentHeight;
        private String mVoiceTitle;
        private String mContent;

        public CustomerSimpleMessageBuilder(Context context) {
            super(context);
        }

        @Override
        protected void init() {
            super.init();
            mLayoutHeight = mContext.getResources().getDimensionPixelSize(R.dimen.dialog_simple_voice_height);
            mLayoutWidth = mContext.getResources().getDimensionPixelSize(R.dimen.dialog_customer_message_width);
            mLayoutContentHeight = mContext.getResources().getDimensionPixelSize(R.dimen.dialog_simple_message_content_height);
        }

        @Override
        public int layoutId() {
            return R.layout.layout_simple_message_dialog;
        }

        public CustomerSimpleMessageBuilder setVoiceTitle(String voiceTitle) {
            this.mVoiceTitle = voiceTitle;
            return this;
        }

        public CustomerSimpleMessageBuilder setContent(String content) {
            this.mContent = content;
            return this;
        }

        @Override
        public void onAfterCreateView(View view) {
            super.onAfterCreateView(view);
            if (!TextUtils.isEmpty(mVoiceTitle)) {
                changeLayoutParams(view, mLayoutWidth, mLayoutHeight);
                mTitleTv.setSingleLine(false);
                mTitleTv.setText(mVoiceTitle);
            } else if (!TextUtils.isEmpty(mContent)) {
                ViewStub contentView = view.findViewById(R.id.dialog_content);
                View inflateView = contentView.inflate();
                ((TextView) inflateView).setText(mContent);
                changeLayoutParams(view, 0, mLayoutContentHeight);
            } else {
                changeLayoutParams(view, mLayoutWidth, 0);
            }
        }
    }

    /**
     * image content dialog
     */
    public static class ImageContentDialogBuilder extends MessageDialogBuilder<ImageContentDialogBuilder> {
        private RoundCornerImageView mNotifyIv;
        private int mNotifyResId;
        private Bitmap mNotifyBitmap;
        private String mContent;

        public ImageContentDialogBuilder(Context context) {
            super(context);
        }

        @Override
        public int layoutId() {
            return R.layout.layout_image_content_dialog;
        }

        @Override
        public void onAfterCreateView(View view) {
            mNotifyIv = view.findViewById(R.id.dialog_notify_img);

            if (mNotifyResId != 0) {
                mNotifyIv.setImageResource(mNotifyResId);
            }

            if (null != mNotifyBitmap) {
                mNotifyIv.setImageBitmap(mNotifyBitmap);
            }

            if (!TextUtils.isEmpty(mContent)) {
                ViewStub contentView = view.findViewById(R.id.dialog_content);
                View inflateView = contentView.inflate();
                ((TextView) inflateView).setText(mContent);
            }
        }

        public ImageContentDialogBuilder setNotifyResId(int notifyResId) {
            this.mNotifyResId = notifyResId;
            return this;
        }

        public ImageContentDialogBuilder setNotifyBitmap(Bitmap notifyBitmap) {
            this.mNotifyBitmap = notifyBitmap;
            return this;
        }

        public ImageContentDialogBuilder setContent(String content) {
            this.mContent = content;
            return this;
        }
    }

    /**
     * simple content
     */
    public static class SimpleContentDialogBuilder extends MessageDialogBuilder<SimpleContentDialogBuilder> {
        private int mMessageContentHeight;
        private String mContent;

        public SimpleContentDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected void init() {
            super.init();
            mMessageContentHeight = mContext.getResources().getDimensionPixelSize(R.dimen.dialog_simple_message_content_height);
        }

        @Override
        public int layoutId() {
            return R.layout.layout_simple_message_dialog;
        }

        @Override
        public void onAfterCreateView(View view) {
            //set content height
            changeLayoutParams(view, 0, mMessageContentHeight);
            if (!TextUtils.isEmpty(mContent)) {
                ViewStub contentView = view.findViewById(R.id.dialog_content);
                View inflateView = contentView.inflate();
                ((TextView) inflateView).setText(mContent);
            }
        }

        public SimpleContentDialogBuilder setContent(String content) {
            this.mContent = content;
            return this;
        }
    }

    /**
     * simple message
     */
    public static class SimpleMessageDialogBuilder extends MessageDialogBuilder<SimpleMessageDialogBuilder> {
        public SimpleMessageDialogBuilder(Context context) {
            super(context);
        }

        @Override
        public int layoutId() {
            return R.layout.layout_simple_message_dialog;
        }

        @Override
        public void onAfterCreateView(View view) {

        }
    }

    /**
     * image dialog
     */
    public static class ImageDialogBuilder extends MessageDialogBuilder<ImageDialogBuilder> {
        private RoundCornerImageView mNotifyIv;

        private ViewGroup mVoiceLayout;
        private View mCustomConfirmView;

        private int mNotifyResId;
        private Bitmap mNotifyBitmap;

        public ImageDialogBuilder(Context context) {
            super(context);
        }

        @Override
        public int layoutId() {
            return R.layout.layout_image_dialog;
        }

        @Override
        public void onAfterCreateView(View view) {
            mVoiceLayout = view.findViewById(R.id.voice_layout);
            mNotifyIv = view.findViewById(R.id.dialog_notify_img);

            if (mNotifyResId != 0) {
                mNotifyIv.setImageResource(mNotifyResId);
            }

            if (null != mNotifyBitmap) {
                mNotifyIv.setImageBitmap(mNotifyBitmap);
            }
        }

        public ImageDialogBuilder setNotifyResId(int notifyResId) {
            this.mNotifyResId = notifyResId;
            return this;
        }

        public ImageDialogBuilder setNotifyBitmap(Bitmap notifyBitmap) {
            this.mNotifyBitmap = notifyBitmap;
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

        public void dynamicCustomConfirmView(View customConfirmView) {
            this.mConfirmBtn.setEnabled(false);
            this.mConfirmBtn.setText(null);
            this.mTitleTv.setText(null);
            if (null != mNotifyIv) {
                mNotifyIv.setMaskColor(mContext.getResources().getColor(R.color.transparent));
            }

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
     * pure voice
     */
    public static class SimpleVoiceDialogBuilder extends MessageDialogBuilder<SimpleVoiceDialogBuilder> {
        private ViewGroup mVoiceLayout;
        private View mCustomConfirmView;

        public SimpleVoiceDialogBuilder(Context context) {
            super(context);
        }

        @Override
        public int layoutId() {
            return R.layout.layout_simple_voice_dialog;
        }

        @Override
        public void onAfterCreateView(View view) {
            mVoiceLayout = view.findViewById(R.id.voice_layout);
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
        private final static int DEFAULT_DURATION = 3000;
        private TextView mMessageTv;
        private ImageView mIconResIv;
        private int mDuration = DEFAULT_DURATION;
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
            mGlassDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
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
            mDuration = mDuration == 0 ? DEFAULT_DURATION : mDuration;
            if (mDuration > 0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, mDuration);
            }
        }
    }

    /**
     * common customer message dialog
     *
     * @param <T>
     */
    public abstract static class CustomerMessageDialogBuilder<T extends MessageDialogBuilder> extends MessageDialogBuilder<T> {
        private Button mPlayBtn;
        private String mPlayText;

        private GlassDialogListener mPlayListener;

        public CustomerMessageDialogBuilder(Context context) {
            super(context);
        }

        public T setPlayText(String playText) {
            this.mPlayText = playText;
            return (T) this;
        }

        public T setPlayListener(GlassDialogListener playListener) {
            this.mPlayListener = playListener;
            return (T) this;
        }

        @Override
        public void onAfterCreateView(View view) {
            if (!TextUtils.isEmpty(mPlayText)) {
                ViewStub contentView = view.findViewById(R.id.play_btn);
                View inflateView = contentView.inflate();
                mPlayBtn = (Button) inflateView;
                if (null == mPlayBtn) {
                    return;
                }

                mPlayBtn.setText(mPlayText);
                mPlayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mPlayListener) {
                            mPlayListener.onClick(v);
                        }
                    }
                });
            }
        }
    }

    /**
     * common message
     *
     * @param
     */
    public static abstract class MessageDialogBuilder<T extends GlassDialogBuilder> extends GlassDialogBuilder<T> {
        protected Button mConfirmBtn;
        protected Button mCancelBtn;
        protected String mConfirmText;
        protected String mCancelText;

        protected GlassDialogListener mConfirmListener;
        protected GlassDialogListener mCancelListener;

        public MessageDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected void init() {

        }

        @Override
        protected void onCreateContent(Context context, ViewGroup parent, GlassDialog dialog) {
            View view = LayoutInflater.from(context).inflate(layoutId(), parent, false);
            mTitleTv = view.findViewById(R.id.dialog_title);
            mConfirmBtn = view.findViewById(R.id.confirm_btn);
            mCancelBtn = view.findViewById(R.id.cancel_btn);

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
                    if (null != mConfirmListener) {
                        mConfirmListener.onClick(v);
                    }
                }
            });

            mCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (null != mCancelListener) {
                        mCancelListener.onClick(v);
                    }
                }
            });

            onAfterCreateView(view);
            parent.addView(view);
        }

        @Override
        protected void onAfter(Context context, ViewGroup parent, GlassDialog dialog) {
            super.onAfter(context, parent, dialog);
            ViewGroup.LayoutParams params = parent.getLayoutParams();
            params.height = Utils.getScreenHeight(context);
            parent.setLayoutParams(params);
        }

        public T setConfirmText(String confirmText) {
            this.mConfirmText = confirmText;
            return (T) this;
        }

        public T setCancelText(String cancelText) {
            this.mCancelText = cancelText;
            return (T) this;
        }


        public T setConfirmListener(GlassDialogListener confirmListener) {
            this.mConfirmListener = confirmListener;
            return (T) this;
        }

        public T setCancelListener(GlassDialogListener cancelListener) {
            this.mCancelListener = cancelListener;
            return (T) this;
        }

        //dynamic content
        public void dynamicCancelText(final String cancelText) {
            if (mGlassDialog.isShowing()) {
                mCancelBtn.setText(cancelText);
            }
        }

        public abstract int layoutId();

        public abstract void onAfterCreateView(final View view);
    }
}
