package com.rokid.glass.ui.autosize;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.SparseArray;

/**
 * adapter problem
 */
public class AutoSizeCompat {
    private static SparseArray<DisplayMetricsInfo> mCache = new SparseArray<>();
    private static final int MODE_SHIFT = 30;
    private static final int MODE_MASK = 0x3 << MODE_SHIFT;
    private static final int MODE_ON_WIDTH = 1 << MODE_SHIFT;
    private static final int MODE_DEVICE_SIZE = 2 << MODE_SHIFT;

    private AutoSizeCompat() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void autoConvertDensityOfGlobal(Resources resources) {
        if (AutoSizeConfig.getInstance().isBaseOnWidth()) {
            autoConvertDensityBaseOnWidth(resources, AutoSizeConfig.getInstance().getDesignWidthInDp());
        } else {
            autoConvertDensityBaseOnHeight(resources, AutoSizeConfig.getInstance().getDesignHeightInDp());
        }
    }

    public static void autoConvertDensityBaseOnWidth(Resources resources, float designWidthInDp) {
        autoConvertDensity(resources, designWidthInDp, true);
    }

    public static void autoConvertDensityBaseOnHeight(Resources resources, float designHeightInDp) {
        autoConvertDensity(resources, designHeightInDp, false);
    }

    public static void autoConvertDensity(Resources resources, float sizeInDp, boolean isBaseOnWidth) {
        int screenSize = isBaseOnWidth ? AutoSizeConfig.getInstance().getScreenWidth()
                : AutoSizeConfig.getInstance().getScreenHeight();

        int key = Math.round((sizeInDp + screenSize) * AutoSizeConfig.getInstance().getInitScaledDensity()) & ~MODE_MASK;
        key = isBaseOnWidth ? (key | MODE_ON_WIDTH) : (key & ~MODE_ON_WIDTH);
        DisplayMetricsInfo displayMetricsInfo = mCache.get(key);

        float targetDensity = 0;
        int targetDensityDpi = 0;
        float targetScaledDensity = 0;
        float targetXdpi = 0;
        int targetScreenWidthDp;
        int targetScreenHeightDp;

        if (displayMetricsInfo == null) {
            if (isBaseOnWidth) {
                targetDensity = AutoSizeConfig.getInstance().getScreenWidth() * 1.0f / sizeInDp;
            } else {
                targetDensity = AutoSizeConfig.getInstance().getScreenHeight() * 1.0f / sizeInDp;
            }
            if (AutoSizeConfig.getInstance().getPrivateFontScale() > 0) {
                targetScaledDensity = targetDensity * AutoSizeConfig.getInstance().getPrivateFontScale();
            } else {
                float systemFontScale = AutoSizeConfig.getInstance().isExcludeFontScale() ? 1 : AutoSizeConfig.getInstance().
                        getInitScaledDensity() * 1.0f / AutoSizeConfig.getInstance().getInitDensity();
                targetScaledDensity = targetDensity * systemFontScale;
            }
            targetDensityDpi = (int) (targetDensity * 160);

            targetScreenWidthDp = (int) (AutoSizeConfig.getInstance().getScreenWidth() / targetDensity);
            targetScreenHeightDp = (int) (AutoSizeConfig.getInstance().getScreenHeight() / targetDensity);

            mCache.put(key, new DisplayMetricsInfo(targetDensity, targetDensityDpi, targetScaledDensity, targetXdpi, targetScreenWidthDp, targetScreenHeightDp));
        } else {
            targetDensity = displayMetricsInfo.getDensity();
            targetDensityDpi = displayMetricsInfo.getDensityDpi();
            targetScaledDensity = displayMetricsInfo.getScaledDensity();
            targetXdpi = displayMetricsInfo.getXdpi();
            targetScreenWidthDp = displayMetricsInfo.getScreenWidthDp();
            targetScreenHeightDp = displayMetricsInfo.getScreenHeightDp();
        }

        setDensity(resources, targetDensity, targetDensityDpi, targetScaledDensity, targetXdpi);
    }

    private static void setDensity(Resources resources, float density, int densityDpi, float scaledDensity, float xdpi) {
        DisplayMetrics activityDisplayMetrics = resources.getDisplayMetrics();
        setDensity(activityDisplayMetrics, density, densityDpi, scaledDensity, xdpi);
        DisplayMetrics appDisplayMetrics = AutoSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics();
        setDensity(appDisplayMetrics, density, densityDpi, scaledDensity, xdpi);
    }

    private static void setDensity(DisplayMetrics displayMetrics, float density, int densityDpi, float scaledDensity, float xdpi) {
        displayMetrics.density = density;
        displayMetrics.densityDpi = densityDpi;
        displayMetrics.scaledDensity = scaledDensity;
    }
}
