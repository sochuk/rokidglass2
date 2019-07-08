package com.rokid.glass.ui.util;

import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;

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

    /**
     * 根据preview的rect，获取到映射到LCD屏幕的区域
     *
     * @param previewWidth
     * @param previewHeight
     * @param previewRect
     * @return
     */
    public static Rect getAlignmentRect(final int previewWidth, final int previewHeight, final Rect previewRect) {
//        Rect rect = getAlignmentBaseRect();
//        int w = rect.right - rect.left;
//        int h = rect.bottom - rect.top;
        if (noAlignment()) {
            return null;
        }

        RectF rectF = getAlignmentPercent();


        float w = ((rectF.right - rectF.left) * previewWidth);
        float h = ((rectF.bottom - rectF.top) * previewHeight);

        int left = (int) ((previewRect.left - rectF.left * previewWidth) * 1.0f / w * BASE_WIDTH);
        int top = (int) ((previewRect.top - rectF.top * previewHeight) * 1.0f / h * BASE_HEIGHT);
        int right = (int) ((previewRect.right - rectF.left * previewWidth) * 1.0f / w * BASE_WIDTH);
        int bottom = (int) ((previewRect.bottom - rectF.top * previewHeight) * 1.0f / h * BASE_HEIGHT);

        return new Rect(left, top, right, bottom);
//        return new Rect((int) ((previewRect.left - rect.left) * 1.0 / w * previewWidth),
//                (int) ((previewRect.top - rect.top) * 1.0 / h * previewHeight),
//                (int) ((previewRect.right - rect.left) * 1.0 / w * previewWidth),
//                (int) ((previewRect.bottom - rect.top) * 1.0 / h * previewHeight));
    }

    /**
     * 获取系统的Alignment百分比
     * 真实区域在虚拟世界的比例
     *
     * @return
     */
    public static RectF getAlignmentPercent() {
        Rect rect = getAlignmentBaseRect();
        return new RectF(rect.left * 1.0f / BASE_WIDTH, rect.top * 1.0f / BASE_HEIGHT,
                rect.right * 1.0f / BASE_WIDTH, rect.bottom * 1.0f / BASE_HEIGHT);
    }

    /**
     * 获取不同 glass 下的 alignment 参数
     *
     * @return
     */
    private static Rect getAlignmentBaseRect() {
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

    private static boolean noAlignment() {
        return TextUtils.isEmpty(getSystemProperty(ALIGNMENT_LEFT));
    }
}
