package com.rokid.glass.ui.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.rokid.glass.ui.R;
import com.rokid.glass.ui.util.Utils;

/**
 * @author jian.yang
 * @date 2019/2/20
 */

public class GlassButton extends AppCompatButton {
    private Paint mFocusedPaint;
    private Paint mFocusedStrokePaint;
    private Paint mUnfocusedStrokePaint;
    private final int mFocusedGlowWidth = 28;
    private final int mBtnPadding = 20;
    private final float mStrokeWidth = 3f;

    private int defaultButtonUnfocusedColor;
    private float defaultButtonCorner;

    public GlassButton(Context context) {
        super(context);
    }

    public GlassButton(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GlassButton);
        defaultButtonUnfocusedColor = a.getColor(R.styleable.GlassButton_unfocused_border_color,
                getResources().getColor(R.color.glass_button_unfocused));
        defaultButtonCorner = a.getFloat(R.styleable.GlassButton_corner, 100f);

        int textSize = a.getDimensionPixelSize(R.styleable.GlassButton_android_textSize,
                getResources().getDimensionPixelSize(R.dimen.common_text_size));
        int textColor = a.getColor(R.styleable.GlassButton_android_textColor, getResources().getColor(R.color.common_text_color));

        int paddingLeft = a.getDimensionPixelSize(R.styleable.GlassButton_android_paddingLeft,
                getResources().getDimensionPixelSize(R.dimen.glass_btn_padding_left));
        int paddingRight = a.getDimensionPixelSize(R.styleable.GlassButton_android_paddingRight,
                getResources().getDimensionPixelSize(R.dimen.glass_btn_padding_right));
        int paddingTop = a.getDimensionPixelSize(R.styleable.GlassButton_android_paddingTop,
                getResources().getDimensionPixelSize(R.dimen.glass_btn_padding_top));
        int paddingBottom = a.getDimensionPixelSize(R.styleable.GlassButton_android_paddingBottom,
                getResources().getDimensionPixelSize(R.dimen.glass_btn_padding_bottom));

        a.recycle();

        setBackgroundDrawable(null);
        setAllCaps(false);

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        setTextColor(textColor);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init(bottom - top);
    }

    protected void init(final int height) {
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
//        mUnfocusedStrokePaint.setColor(defaultButtonUnfocusedColor);

        LinearGradient storkeGradient = new LinearGradient(0, mBtnPadding, 0, (int) (height * 1.4f),
                new int[]{startColor, centerColor, endColor}, null, Shader.TileMode.CLAMP);

        LinearGradient glowGradient = new LinearGradient(0, 0, 0, height,
                new int[]{glowStartColor, glowEndColor}, null, Shader.TileMode.CLAMP);

        LinearGradient unStorkeGradient = new LinearGradient(0, mBtnPadding, 0, (int) (height * 1.4f),
                new int[]{unstartColor, uncenterColor, unendColor}, null, Shader.TileMode.CLAMP);

        mFocusedPaint.setShader(glowGradient);
        mFocusedStrokePaint.setShader(storkeGradient);
        mUnfocusedStrokePaint.setShader(unStorkeGradient);

        mFocusedPaint.setMaskFilter(new BlurMaskFilter(mFocusedGlowWidth, BlurMaskFilter.Blur.OUTER));
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (isFocused()) {
            this.setTextColor(getResources().getColor(R.color.common_c1_text_color));
            setTypeface(null, Typeface.BOLD);
        } else {
            this.setTextColor(getResources().getColor(R.color.common_text_color));
            setTypeface(null, Typeface.NORMAL);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (isFocused()) {
            canvas.drawRoundRect(new RectF(mFocusedGlowWidth, mBtnPadding, getWidth() - mFocusedGlowWidth, getHeight() - mBtnPadding),
                    defaultButtonCorner, defaultButtonCorner, mFocusedPaint);

            canvas.drawRoundRect(new RectF(mFocusedGlowWidth, mBtnPadding, getWidth() - mFocusedGlowWidth, getHeight() - mBtnPadding),
                    defaultButtonCorner, defaultButtonCorner, mFocusedStrokePaint);
        } else {
            canvas.drawRoundRect(new RectF(mFocusedGlowWidth, mBtnPadding, getWidth() - mFocusedGlowWidth, getHeight() - mBtnPadding),
                    defaultButtonCorner, defaultButtonCorner, mUnfocusedStrokePaint);
        }
        super.onDraw(canvas);
    }
}
