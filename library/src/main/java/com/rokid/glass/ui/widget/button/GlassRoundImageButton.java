package com.rokid.glass.ui.widget.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.util.Log;

import com.rokid.glass.ui.R;

public class GlassRoundImageButton extends AppCompatImageButton {
    private Paint mFocusedPaint;
    private Paint mFocusedStrokePaint;
    private Paint mUnfocusedStrokePaint;
    private final int mFocusedGlowWidth = 28;
    private final int mBtnPadding = 20;
    private final float mStrokeWidth = 3f;
    private int mPadding;
    private int mUnFocusedRes;
    private int mFocusedRes;

    public GlassRoundImageButton(Context context) {
        this(context, null);
    }

    public GlassRoundImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlassRoundImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, context);
    }

    private void init(AttributeSet attrs, final Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GlassRoundImageButton);
        mPadding = a.getDimensionPixelSize(R.styleable.GlassRoundImageButton_android_padding,
                getResources().getDimensionPixelSize(R.dimen.glass_round_btn_padding));
        mFocusedRes = a.getResourceId(R.styleable.GlassRoundImageButton_focused_src, 0);
        mUnFocusedRes = a.getResourceId(R.styleable.GlassRoundImageButton_unfocused_src, 0);

        a.recycle();

        setBackgroundDrawable(null);

        setPadding(mPadding, mPadding, mPadding, mPadding);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setImageResource(mUnFocusedRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initColor(bottom - top);
    }

    private void initColor(int height) {
        int startColor = getResources().getColor(R.color.glass_button_focused_start_color);
        int centerColor = getResources().getColor(R.color.glass_button_focused_mid);
        int endColor = getResources().getColor(R.color.glass_button_focused_top_edge);

        int glowStartColor = getResources().getColor(R.color.glass_button_focused_glow_top_edge);
        int glowEndColor = getResources().getColor(R.color.glass_button_focused_bottom_edge);

        int unstartColor = getResources().getColor(R.color.glass_button_unfocused_start_color);
        int uncenterColor = getResources().getColor(R.color.glass_button_unfocused_center_color);
        int unendColor = getResources().getColor(R.color.glass_button_unfocused_end_color);

        mFocusedPaint = new Paint();
        mFocusedPaint.setAntiAlias(true);
        mFocusedPaint.setStyle(Paint.Style.FILL);

        mFocusedStrokePaint = new Paint();
        mFocusedStrokePaint.setAntiAlias(true);
        mFocusedStrokePaint.setStyle(Paint.Style.STROKE);
        mFocusedStrokePaint.setStrokeWidth(mStrokeWidth);

        mUnfocusedStrokePaint = new Paint();
        mUnfocusedStrokePaint.setAntiAlias(true);
        mUnfocusedStrokePaint.setStyle(Paint.Style.STROKE);
        mUnfocusedStrokePaint.setStrokeWidth(mStrokeWidth);

        LinearGradient storkeGradient = new LinearGradient(0, 0, 0, (int) (height * 1.4f),
                new int[]{startColor, centerColor, endColor}, null, Shader.TileMode.CLAMP);

        LinearGradient glowGradient = new LinearGradient(0, 0, 0, height,
                new int[]{glowStartColor, glowEndColor}, null, Shader.TileMode.CLAMP);

        LinearGradient unStorkeGradient = new LinearGradient(0, 0, 0, (int) (height * 1.4f),
                new int[]{unstartColor, uncenterColor, unendColor}, null, Shader.TileMode.CLAMP);

        mFocusedPaint.setShader(glowGradient);
        mFocusedStrokePaint.setShader(storkeGradient);
        mUnfocusedStrokePaint.setShader(unStorkeGradient);

        mFocusedPaint.setMaskFilter(new BlurMaskFilter(mFocusedGlowWidth, BlurMaskFilter.Blur.OUTER));

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (isFocused()) {
            if (mFocusedRes != 0) {
                setImageResource(mFocusedRes);
            }
        } else {
            if (mUnFocusedRes != 0) {
                setImageResource(mUnFocusedRes);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFocused()) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() - mPadding) / 2, mFocusedPaint);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() - mPadding) / 2, mFocusedStrokePaint);
        } else {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() - mPadding) / 2, mUnfocusedStrokePaint);
        }
        super.onDraw(canvas);
    }

    public void setSrc(@DrawableRes int focused, @DrawableRes int unfocused) {
        mFocusedRes = focused;
        mUnFocusedRes = unfocused;
        invalidate();
    }
}
