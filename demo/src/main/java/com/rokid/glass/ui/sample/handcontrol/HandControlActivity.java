package com.rokid.glass.ui.sample.handcontrol;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.rokid.glass.imusdk.core.IMUView;
import com.rokid.glass.ui.sample.R;
import com.rokid.glass.ui.sample.adapter.IMUImageAdapter;
import com.rokid.glass.ui.sample.adapter.IMUImageItem;
import com.rokid.glass.ui.sample.handcontrol.sensors.Orientation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunchao
 * @CreateDate: 2020/7/18 2:52 PM
 * imu头控实现示例
 */
public class HandControlActivity extends AppCompatActivity implements Orientation.Listener{

    private Orientation mOrientation;
    private View view;
    FrameLayout.LayoutParams lp;
    private float xCurrent = 0;//当前横向角度
    private float yCurrent = 0;//当前纵向角度
    public static final int SPEED_MAINANGLE = 10;
    /**
     * 每步滑动的距离
     */
    public static final float DISTANCE_ONESTEP = 20f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handcontrol);
        view = findViewById(R.id.view);

        initImu();
        initViews();
        initData();
    }

    private void initImu() {

        //TODO add to onResume ?

        mOrientation = Orientation.getInstance();
        mOrientation.initialize(this);
        mOrientation.addListener(this);
        mOrientation.startListening();
    }

    private void initViews() {
        view = findViewById(R.id.view);
    }

    private void initData() {

    }

    @Override
    public void onOrientationChanged(float pitch, float roll, float yaw) {
        scrollScreen(yaw, pitch);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mOrientation != null)
            mOrientation.addListener(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mOrientation != null)
            mOrientation.removeListener(this);

    }

    private void scrollScreen(float x, float y) {
        if (xCurrent == 0 && yCurrent == 0) {
            xCurrent = x;
            yCurrent = y;
            return;
        }
        float xDeviation = getDeviation(x, xCurrent);//x偏移
        float yDeviation = getDeviation(y, yCurrent);//y偏移
//        Logger.d("mainact  xDeviation =  " + xDeviation + " yDeviation = " + yDeviation);

        Boolean errorImu = xDeviation < -1 * SPEED_MAINANGLE || xDeviation > SPEED_MAINANGLE || yDeviation < -1 * SPEED_MAINANGLE || yDeviation > SPEED_MAINANGLE;
        if (errorImu) {
            xCurrent = x;
            yCurrent = y;
            return;
        }
        if ((xDeviation < -0.05 || xDeviation > 0.05) || (yDeviation < -0.02 || yDeviation > 0.02)) {
            int xDeviationInt = Math.round(xDeviation * DISTANCE_ONESTEP);
            int yDeviationInt = Math.round(yDeviation * DISTANCE_ONESTEP * 2);
            if (Math.abs(xDeviationInt) < 1 && Math.abs(yDeviationInt) < 1)
                return;

            if (lp == null)
                lp = (FrameLayout.LayoutParams) view.getLayoutParams();
            int leftMargin = lp.leftMargin + xDeviationInt;
            int topMargin = lp.topMargin + yDeviationInt;
            lp.leftMargin = leftMargin;
            lp.topMargin = topMargin;
            view.setLayoutParams(lp);


//            ll_parent.setTranslationX(xDeviation);
//            ll_parent.setTranslationY(yDeviation);
            yCurrent = y;
            xCurrent = x;

        }


    }

    /**
     * 计算偏移值
     *
     * @return
     */
    public static float getDeviation(float value, float base) {
        if (base > 0) {
            if (value > 0) {
                return value - base;
            } else {
                if (Math.abs(value - base) < 180) {
                    return value - base;
                } else {
                    return 180 + value + 180 - base;
                }

            }
        } else {
            if (value < 0) {
                return value - base;
            } else {
                if (Math.abs(value - base) < 180) {
                    return value - base;
                } else {
                    return -180 + value - 180 - base;
                }

            }
        }
    }
}
