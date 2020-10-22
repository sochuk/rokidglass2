package com.rokid.glass.ui.util;

import android.app.Application;
import android.util.Log;

public class ContextUtil {

    private static Application application;

    public static Application getApplicationContext() {
        if (application != null) {
            return application;
        }
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null)
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
        } catch (final Exception e) {
            Log.e("DEBUG", "Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                Log.e("DEBUG", "Failed to get current application from ActivityThread." + e.getMessage());
            }
        } finally {
            application = app;
        }
        return application;
    }
}
