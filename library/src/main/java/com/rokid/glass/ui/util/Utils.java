package com.rokid.glass.ui.util;

import android.content.Context;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;

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

    public final static String DEVICE_MODEL = "ro.product.model";

    /**
     * 判断是否是一代眼镜
     * @return
     */
    public static boolean isGenerationOneGlass() {
        if ("Rokid Glass".equalsIgnoreCase(getSystemProperty(DEVICE_MODEL))) {
            return true;
        }
        return false;
    }

    public static String getSystemProperty(String name) {
        String serial = "";
        try {
            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serial = (String)get.invoke(c, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }
}

