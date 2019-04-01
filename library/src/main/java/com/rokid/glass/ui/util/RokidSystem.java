package com.rokid.glass.ui.util;

import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;

import java.util.WeakHashMap;

/**
 * @author jian.yang
 * @date 2019/3/14
 */

public class RokidSystem {
    private final static String ALIGNMENT_LEFT = "ro.rokid.alignment.left";
    private final static String ALIGNMENT_TOP = "ro.rokid.alignment.top";
    private final static String ALIGNMENT_RIGHT = "ro.rokid.alignment.right";
    private final static String ALIGNMENT_BOTTOM = "ro.rokid.alignment.bottom";
    private final static String HARDWARE_VERSION = "ro.rokid.hardware.version";
    private final static int BASE_WIDTH = 1280;
    private final static int BASE_HEIGHT = 720;

    public static Rect getAlignmentRect(final int previewWidth, final int previewHeight, final Rect previewRect) {
//        Rect rect = getAlignmentRect();
//        int w = rect.right - rect.left;
//        int h = rect.bottom - rect.top;

        RectF rectF = getAlignmentPercent();

        int w = (int) ((rectF.right - rectF.left) * previewWidth);
        int h = (int) ((rectF.bottom - rectF.top) * previewHeight);

        int left = (int) ((previewRect.left - rectF.left * previewWidth) / w * BASE_WIDTH);
        int top = (int) ((previewRect.top - rectF.top * previewWidth) / h * BASE_HEIGHT);
        int right = (int) ((previewRect.right - rectF.left * previewWidth) / w * BASE_WIDTH);
        int bottom = (int) ((previewRect.bottom - rectF.top * previewWidth) / h * BASE_HEIGHT);

        return new Rect(left,top,right,bottom);

//        return new Rect((int) ((previewRect.left - rect.left) * 1.0 / w * previewWidth),
//                (int) ((previewRect.top - rect.top) * 1.0 / h * previewHeight),
//                (int) ((previewRect.right - rect.left) * 1.0 / w * previewWidth),
//                (int) ((previewRect.bottom - rect.top) * 1.0 / h * previewHeight));
    }

    /**
     * 获取系统的Alignment百分比
     *
     * @return
     */
    public static RectF getAlignmentPercent() {
        Rect rect = getAlignmentRect();
        return new RectF(rect.left / BASE_WIDTH, rect.top / BASE_HEIGHT,
                rect.right / BASE_WIDTH, rect.bottom / BASE_HEIGHT);
    }

    /**
     * 获取不同 glass 下的 alignment 参数
     *
     * @return
     */
    public static Rect getAlignmentRect() {
        return new Rect(toInt(getSystemProperty(ALIGNMENT_LEFT)),
                toInt(getSystemProperty(ALIGNMENT_TOP)),
                toInt(getSystemProperty(ALIGNMENT_RIGHT)),
                toInt(getSystemProperty(ALIGNMENT_BOTTOM)));

    }

    /**
     * 获取glass版本
     *
     * @return dvt or evt
     */
    public static String getHardwareVersion() {
        return getSystemProperty(HARDWARE_VERSION);
    }

    private static int toInt(final String value) {
        return !TextUtils.isEmpty(value) && TextUtils.isDigitsOnly(value)
                ? Integer.parseInt(value) : 0;
    }

    public static String getSystemProperty(String key) {
        String value = null;

        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}
