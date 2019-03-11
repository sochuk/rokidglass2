package com.rokid.glass.ui.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author jian.yang
 * @date 2019/3/7
 */

public class Utils {
    public static int getScreenHeight(final Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

