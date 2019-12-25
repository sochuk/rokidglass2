package com.rokid.glass.ui.toast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rokid.glass.ui.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 最长支持3.5s 默认1s
 */
public class GlassToastUtil {
    public static int DURATION_DEFAULT = 1500;
    private static TextView mTextView;

    public static void showToast(@NonNull Context context, String message, int duration) {
        if (duration >= 3500)
            duration = 3500;
        //Toast的初始化

        //加载Toast布局

        View toastRoot = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);

        //初始化布局控件

        mTextView = (TextView) toastRoot.findViewById(R.id.tv_message);

        //为控件设置属性

        mTextView.setText(message);

        final Toast toastStart = Toast.makeText(context,"",Toast.LENGTH_LONG);

        toastStart.setView(toastRoot);

        //获取屏幕高度


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int height = wm.getDefaultDisplay().getHeight();

        //Toast的Y坐标是屏幕高度的1/4，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 6);
//        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.show();

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                toastStart.cancel();
            }

        }, duration);

    }

    public static void showToast(@NonNull Context context, String message) {
        showToast(context, message, DURATION_DEFAULT);
    }
    public static void showToast(@NonNull Context context, @StringRes int resId) {
        showToast(context, context.getResources().getString(resId));
    }


}

