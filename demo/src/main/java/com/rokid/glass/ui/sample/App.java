package com.rokid.glass.ui.sample;

import android.app.Application;

import com.rokid.glass.ui.autosize.AutoSizeConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AutoSizeConfig.getInstance().setExcludeFontScale(true);
    }
}
