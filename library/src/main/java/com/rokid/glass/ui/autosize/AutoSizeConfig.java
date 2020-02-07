package com.rokid.glass.ui.autosize;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * auto size config
 */
public class AutoSizeConfig {
    private static final String KEY_DESIGN_WIDTH_IN_DP = "design_width_in_dp";
    private static final String KEY_DESIGN_HEIGHT_IN_DP = "design_height_in_dp";
    private Application mApplication;
    private boolean isBaseOnWidth = true;

    /**
     * 设计图上的总宽度, 单位 dp
     */
    private int mDesignWidthInDp;
    /**
     * 设计图上的总高度, 单位 dp
     */
    private int mDesignHeightInDp;

    private boolean isVertical;

    /**
     * 设备的屏幕总宽度, 单位 px
     */
    private int mScreenWidth;
    /**
     * 设备的屏幕总高度
     */
    private int mScreenHeight;
    private int mStatusBarHeight;

    /**
     * 最初的 {@link DisplayMetrics#density}
     */
    private float mInitDensity = -1;
    /**
     * 最初的 {@link DisplayMetrics#densityDpi}
     */
    private int mInitDensityDpi;
    /**
     * 最初的 {@link DisplayMetrics#scaledDensity}
     */
    private float mInitScaledDensity;
    /**
     * 最初的 {@link DisplayMetrics#xdpi}
     */
    private float mInitXdpi;
    /**
     * 最初的 {@link Configuration#screenWidthDp}
     */
    private int mInitScreenWidthDp;
    /**
     * 最初的 {@link Configuration#screenHeightDp}
     */
    private int mInitScreenHeightDp;

    private AutoSizeActivityLifecycle mActivityLifecycle;
    private boolean isStop;

    private AutoSizeConfig() {
    }

    private static class Holder {
        private static AutoSizeConfig instance = new AutoSizeConfig();
    }

    public static AutoSizeConfig getInstance() {
        return Holder.instance;
    }

    /**
     * 框架会在 APP 启动时自动调用此方法进行初始化, 使用者无需手动初始化, 初始化方法只能调用一次, 否则报错
     *
     * @param application {@link Application}
     */
    AutoSizeConfig init(Application application) {
        return init(application, true, null);
    }

    AutoSizeConfig init(final Application application, boolean isBaseOnWidth, AutoAdaptStrategy strategy) {
        if (null == application) {
            return null;
        }

        this.mApplication = application;
        this.isBaseOnWidth = isBaseOnWidth;
        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        final Configuration configuration = Resources.getSystem().getConfiguration();

        mDesignWidthInDp = 640;
        mDesignHeightInDp = 360;

        //AndroidManifest mete data
        getMetaData(application);

        isVertical = application.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int[] screenSize = AutoSizeUtils.getScreenSize(application);
        mScreenWidth = screenSize[0];
        mScreenHeight = screenSize[1];
        mStatusBarHeight = AutoSizeUtils.getStatusBarHeight();

        //init params
        mInitDensity = displayMetrics.density;
        mInitDensityDpi = displayMetrics.densityDpi;
        mInitScaledDensity = displayMetrics.scaledDensity;
        mInitXdpi = displayMetrics.xdpi;
        mInitScreenWidthDp = configuration.screenWidthDp;
        mInitScreenHeightDp = configuration.screenHeightDp;

        /**
         * font changed https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
         */
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null) {
                    if (newConfig.fontScale > 0) {
                        mInitScaledDensity =
                                Resources.getSystem().getDisplayMetrics().scaledDensity;
                    }
                    isVertical = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT;
                    int[] screenSize = AutoSizeUtils.getScreenSize(application);
                    mScreenWidth = screenSize[0];
                    mScreenHeight = screenSize[1];
                }
            }

            @Override
            public void onLowMemory() {

            }
        });

        mActivityLifecycle = new AutoSizeActivityLifecycle(new DefaultAutoAdaptStrategy());
        application.registerActivityLifecycleCallbacks(mActivityLifecycle);
        return this;
    }

    /**
     * 设置全局设计图宽度
     *
     * @param designWidthInDp 设计图宽度
     */
    public AutoSizeConfig setDesignWidthInDp(int designWidthInDp) {
        mDesignWidthInDp = designWidthInDp;
        return this;
    }

    /**
     * 设置全局设计图高度
     *
     * @param designHeightInDp 设计图高度
     */
    public AutoSizeConfig setDesignHeightInDp(int designHeightInDp) {
        mDesignHeightInDp = designHeightInDp;
        return this;
    }

    public void stop(Activity activity) {
        synchronized (AutoSizeConfig.class) {
            if (!isStop) {
                mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
                AutoSize.cancelAdapt(activity);
                isStop = true;
            }
        }
    }

    public float getInitXdpi() {
        return mInitXdpi;
    }

    public float getInitDensity() {
        return mInitDensity;
    }

    public int getInitDensityDpi() {
        return mInitDensityDpi;
    }

    public float getInitScaledDensity() {
        return mInitScaledDensity;
    }

    public int getInitScreenWidthDp() {
        return mInitScreenWidthDp;
    }

    public int getInitScreenHeightDp() {
        return mInitScreenHeightDp;
    }

    public boolean isBaseOnWidth() {
        return isBaseOnWidth;
    }

    public int getDesignWidthInDp() {
        return mDesignWidthInDp;
    }

    public int getDesignHeightInDp() {
        return mDesignHeightInDp;
    }

    public Application getApplication() {
        return mApplication;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    private void getMetaData(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PackageManager packageManager = context.getPackageManager();
                ApplicationInfo applicationInfo;
                try {
                    applicationInfo = packageManager.getApplicationInfo(context
                            .getPackageName(), PackageManager.GET_META_DATA);
                    if (applicationInfo != null && applicationInfo.metaData != null) {
                        if (applicationInfo.metaData.containsKey(KEY_DESIGN_WIDTH_IN_DP)) {
                            mDesignWidthInDp = (int) applicationInfo.metaData.get(KEY_DESIGN_WIDTH_IN_DP);
                        }
                        if (applicationInfo.metaData.containsKey(KEY_DESIGN_HEIGHT_IN_DP)) {
                            mDesignHeightInDp = (int) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT_IN_DP);
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
