package com.rokid.glass.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rokid.glass.ui.R;

/**
 * @author jian.yang
 * @date 2019/3/6
 */

public abstract class GlassDialogBuilder<T extends GlassDialogBuilder> {
    protected GlassDialog mGlassDialog;
    protected Context mContext;
    private ViewGroup mRootView;

    protected TextView mTitleTv;
    protected String mTitle;

    private boolean mCancelable;
    private boolean mCanceledOnTouchOutside;

    public GlassDialogBuilder(final Context context) {
        this.mContext = context;
        init();
    }

    public GlassDialog show() {
        final GlassDialog dialog = create();
        dialog.show();
        return dialog;
    }

    public GlassDialog create() {
        return create(R.style.GlassDialogStyle);
    }

    public GlassDialog create(final int style) {
        mGlassDialog = new GlassDialog(mContext, style, this);
        Context context = mGlassDialog.getContext();
        mRootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_glass_dialog, null);
        onCreateContent(context, mRootView, mGlassDialog);

        mGlassDialog.setCancelable(mCancelable);
        mGlassDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        mGlassDialog.addContentView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        onAfter(context, mRootView, mGlassDialog);
        return mGlassDialog;
    }

    public void dynamicTitle(final String title) {
        if (!TextUtils.isEmpty(title) && mGlassDialog.isShowing()) {
            mTitleTv.setText(title);
        }
    }

    public T setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        return (T) this;
    }

    public T setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.mCanceledOnTouchOutside = canceledOnTouchOutside;
        return (T) this;
    }

    public T setTitle(String title) {
        this.mTitle = title;
        return (T) this;
    }

    protected void dismiss() {
        if (null != mGlassDialog && mGlassDialog.isShowing()) {
            mGlassDialog.dismiss();
        }
    }

    protected void changeLayoutParams(final View view, final int width, final int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (width > 0) {
            params.width = width;
        }
        if (height > 0) {
            params.height = height;
        }
        view.setLayoutParams(params);
    }

    protected void dialogShow() {

    }

    protected void onAfter(Context context, ViewGroup parent, GlassDialog dialog) {

    }

    protected abstract void init();

    protected abstract void onCreateContent(Context context, ViewGroup parent, GlassDialog dialog);
}
