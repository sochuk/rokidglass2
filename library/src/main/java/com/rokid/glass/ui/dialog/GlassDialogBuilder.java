package com.rokid.glass.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rokid.glass.ui.R;

/**
 * @author jian.yang
 * @date 2019/3/6
 */

public abstract class GlassDialogBuilder<T extends GlassDialogBuilder> {
    protected GlassDialog mGlassDialog;
    private Context mContext;
    private ViewGroup mRootView;

    protected TextView mTitleTv;
    protected String mTitle;

    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;

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
        mGlassDialog.setCancelable(mCancelable);
        mGlassDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        onCreateContent(context, mRootView, mGlassDialog);

        mGlassDialog.addContentView(mRootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return mGlassDialog;
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

    protected void dialogShow() {

    }

    protected abstract void init();
    protected abstract void onCreateContent(Context context, ViewGroup parent, GlassDialog dialog);
}
