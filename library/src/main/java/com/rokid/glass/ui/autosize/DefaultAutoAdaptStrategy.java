package com.rokid.glass.ui.autosize;

import android.app.Activity;

/**
 * default auto adapt
 */
public class DefaultAutoAdaptStrategy implements AutoAdaptStrategy {
    @Override
    public void applyAdapt(Object target, Activity activity) {
        if (target instanceof CancelAdapt) {
            AutoSize.cancelAdapt(activity);
            return;
        }

        AutoSize.autoConvertDensityOfGlobal(activity);
    }
}
