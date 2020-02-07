package com.rokid.glass.ui.autosize;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

/**
 * 屏幕适配逻辑策略类
 */
public interface AutoAdaptStrategy {

    /**
     * 开始执行屏幕适配逻辑
     *
     * @param target   需要屏幕适配的对象
     * @param activity 需要拿到当前的 {@link Activity} 才能修改 {@link DisplayMetrics#density}
     */
    void applyAdapt(Object target, Activity activity);
}
