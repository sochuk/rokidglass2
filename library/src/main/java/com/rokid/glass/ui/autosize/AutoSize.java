package com.rokid.glass.ui.autosize;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * auto size
 */
public class AutoSize {
    private static Map<String, DisplayMetricsInfo> mCache = new ConcurrentHashMap<>();

    private AutoSize() {
    }

    private static class Holder {
        private static final AutoSize instance = new AutoSize();
    }

    public static AutoSize getInstance() {
        return Holder.instance;
    }

    /**
     * cancel adapt
     *
     * @param activity
     */
    public static void cancelAdapt(Activity activity) {
        setDensity(activity, AutoSizeConfig.getInstance().getInitDensity(),
                AutoSizeConfig.getInstance().getInitDensityDpi(),
                AutoSizeConfig.getInstance().getInitScaledDensity());
//        setScreenSizeDp(activity
//                , AutoSizeConfig.getInstance().getInitScreenWidthDp()
//                , AutoSizeConfig.getInstance().getInitScreenHeightDp());
    }

    public static void autoConvertDensityOfGlobal(Activity activity) {
        if (AutoSizeConfig.getInstance().isBaseOnWidth()) {
            autoConvertDensityBaseOnWidth(activity, AutoSizeConfig.getInstance().getDesignWidthInDp());
        } else {
            autoConvertDensityBaseOnHeight(activity, AutoSizeConfig.getInstance().getDesignHeightInDp());
        }
    }

    public static void autoConvertDensityBaseOnWidth(Activity activity, float designWidthInDp) {
        autoConvertDensity(activity, designWidthInDp, true);
    }

    public static void autoConvertDensityBaseOnHeight(Activity activity, float designHeightInDp) {
        autoConvertDensity(activity, designHeightInDp, false);
    }

    /**
     * core method from https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     *
     * @param activity
     * @param sizeInDp
     * @param isBaseOnWidth
     */
    public static void autoConvertDensity(Activity activity, float sizeInDp, boolean isBaseOnWidth) {
        int screenSize = isBaseOnWidth ? AutoSizeConfig.getInstance().getScreenWidth()
                : AutoSizeConfig.getInstance().getScreenHeight();

        String key = sizeInDp + "|" + isBaseOnWidth + "|"
                + AutoSizeConfig.getInstance().getInitScaledDensity() + "|" + screenSize;

        Log.d("DEBUG", "##### autoConvertDensity key: " + key);
        DisplayMetricsInfo displayMetricsInfo = mCache.get(key);

        float targetDensity = 0;
        int targetDensityDpi = 0;
        float targetScaledDensity = 0;

        if (null == displayMetricsInfo) {
            if (isBaseOnWidth) {
                targetDensity = AutoSizeConfig.getInstance().getScreenWidth() * 1.0f / sizeInDp;
            } else {
                targetDensity = AutoSizeConfig.getInstance().getScreenHeight() * 1.0f / sizeInDp;
            }

            targetScaledDensity = targetDensity * (AutoSizeConfig.getInstance().
                    getInitScaledDensity() * 1.0f / AutoSizeConfig.getInstance().getInitDensity());
            targetDensityDpi = (int) (targetDensity * 160);
        } else {
            targetDensity = displayMetricsInfo.getDensity();
            targetDensityDpi = displayMetricsInfo.getDensityDpi();
            targetScaledDensity = displayMetricsInfo.getScaledDensity();
        }

        setDensity(activity, targetDensity, targetDensityDpi, targetScaledDensity);
        mCache.put(key, new DisplayMetricsInfo(targetDensity, targetDensityDpi, targetScaledDensity));
    }

    private static void setDensity(Activity activity, float density, int densityDpi, float scaledDensity) {
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        setDensity(activityDisplayMetrics, density, densityDpi, scaledDensity);

        DisplayMetrics appDisplayMetrics = AutoSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics();
        setDensity(appDisplayMetrics, density, densityDpi, scaledDensity);
    }

    private static void setDensity(DisplayMetrics displayMetrics, float density, int densityDpi, float scaledDensity) {
        displayMetrics.density = density;
        displayMetrics.densityDpi = densityDpi;
        displayMetrics.scaledDensity = scaledDensity;
    }
}
