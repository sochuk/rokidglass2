package com.rokid.glass.ui.sample.handcontrol.sensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.view.WindowManager;


import com.rokid.glass.imusdk.core.utils.Logger;

import java.util.ArrayList;

public class Orientation implements SensorEventListener {
    public interface Listener {
        void onOrientationChanged(float pitch, float roll, float yaw);
    }

    public Orientation(){

    }

    private static final int SENSOR_DELAY_MICROS = 30 * 1000; // 16ms

    @Nullable
    private Sensor mRotationSensor;

    private WindowManager mWindowManager;
    private SensorManager mSensorManager;

    private int mLastAccuracy;

    private boolean registered;

    private ArrayList<Listener> mListenerList;

    public float mTempPitch = 0f;
    public float mTempRoll = 0f;
    public float mTempYaw = 0f;
    private int mIncrease;

    public void initialize(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mSensorManager = (SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);

        // Can be null if the sensor hardware is not available
        mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);

        mListenerList = new ArrayList<>();

        registered = false;
    }

    public void uninitialize() {
        stopListening();
        mWindowManager = null;
        mSensorManager = null;
        mRotationSensor = null;
        mListenerList.clear();
    }

    public boolean isUninitialized(){
        return mSensorManager==null;
    }

    public void addListener(Listener listener) {
        for (int i = 0; i < mListenerList.size(); i++) {
            if (mListenerList.get(i) == listener) {
                return;
            }
        }
        mListenerList.add(listener);
    }

    public void removeListener(Listener listener) {
        for (int i = 0; i < mListenerList.size(); i++) {
            if (mListenerList.get(i) == listener) {
                mListenerList.remove(i);
                return;
            }
        }
    }

    public void startListening() {
        if (mRotationSensor == null) {
            Logger.e("Rotation vector sensor not available; will not provide orientation data.");
            return;
        }
        if (!registered) {
            mSensorManager.registerListener(this, mRotationSensor, SensorManager.SENSOR_DELAY_UI);
            registered = true;
        }

        mIncrease = 0;
    }

    public void stopListening() {
        if (registered) {
            mSensorManager.unregisterListener(this);
            registered = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (mLastAccuracy != accuracy) {
            mLastAccuracy = accuracy;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mListenerList.isEmpty()) {
            return;
        }
        if (mLastAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }
        if (event.sensor == mRotationSensor) {
            updateOrientation(event.values);
        }
    }

    float[] rotationMatrix = new float[9];
    float[] adjustedRotationMatrix = new float[9];
    float[] orientation = new float[3];
    float pitch;
    float roll;
    float yaw;

    @SuppressWarnings("SuspiciousNameCombination")
    private void updateOrientation(float[] rotationVector) {

        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);

//        final int worldAxisForDeviceAxisX;
//        final int worldAxisForDeviceAxisY;

        // Remap the axes as if the device screen was the instrument panel,
        // and adjust the rotation matrix for the device orientation.
//        Logger.d("display.getRotation( = "+display.getRotation());
//        switch (display.getRotation()) {
//
//            case Surface.ROTATION_0:
//            default:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_X;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_Z;
//                break;
//            case Surface.ROTATION_90:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_Z;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X;
//                break;
//            case Surface.ROTATION_180:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Z;
//                break;
//            case Surface.ROTATION_270:
//                worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Z;
//                worldAxisForDeviceAxisY = SensorManager.AXIS_X;
//                break;
//        }


        SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X,
                SensorManager.AXIS_Z, adjustedRotationMatrix);

        // Transform rotation matrix into azimuth/pitch/roll

        SensorManager.getOrientation(adjustedRotationMatrix, orientation);

        // Convert radians to degrees
        pitch = orientation[1] * -57;
        roll = orientation[2] * -57;
        yaw = orientation[0] * -57;


//        Logger.d("##### origin p:%s, r: %s, yaw: %s", pitch, roll, yaw);
        if (mIncrease < 2) {
            mTempPitch = pitch;
            mTempRoll = roll;
            mTempYaw = yaw;
//            Logger.d("##### updateOrientation %s, %s, yaw: %s", mTempPitch, mTempRoll, mTempYaw);
            mIncrease++;
        } else {
            pitch -= mTempPitch;
            roll -= mTempRoll;
            yaw -= mTempYaw;
            for (int i = 0; i < mListenerList.size(); i++) {
                if (mListenerList.get(i) != null) {
                    mListenerList.get(i).onOrientationChanged(pitch, roll, yaw);
                }
            }
        }
    }

    public static Orientation getInstance() {
        return OrientationHolder.INSTANCE;
    }

    private static final class OrientationHolder {
        private static final Orientation INSTANCE = new Orientation();
    }
}
