package com.rokid.glass.ui.widget.button;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class GlassRoundButton extends AppCompatButton {
    private Paint mFocusedPaint;
    private Paint mFocusedStrokePaint;
    private Paint mUnfocusedStrokePaint;

    public GlassRoundButton(Context context) {
        this(context, null);
    }

    public GlassRoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlassRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(final Context context) {
        setBackgroundDrawable(null);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }
}
