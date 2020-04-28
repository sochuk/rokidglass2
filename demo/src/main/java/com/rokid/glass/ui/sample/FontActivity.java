package com.rokid.glass.ui.sample;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rokid.glass.ui.util.Utils;

public class FontActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);
        printFont(this, Utils.dp2px(this, 21));
    }

    private void printFont(Context context, int emSize) {
        Paint paint = new Paint();
        paint.setTextSize(emSize);

        Paint.FontMetrics metrics = paint.getFontMetrics();
        Log.d("DEBUG", "##### metrics: size:" + emSize
                + ",top = " + metrics.top
                + ", ascent=" + metrics.ascent
                + ", descent=" + metrics.descent
                + ", bottom=" + metrics.bottom
                + ", leading=" + metrics.leading
                + ", top= " + (-metrics.top + metrics.bottom));
    }
}
