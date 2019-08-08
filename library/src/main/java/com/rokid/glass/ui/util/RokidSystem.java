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

    private final static String ALIGNMENT_LEFT_2K = "ro.rokid.2kalignment.left";
    private final static String ALIGNMENT_TOP_2K = "ro.rokid.2kalignment.top";
    private final static String ALIGNMENT_RIGHT_2K = "ro.rokid.2kalignment.right";
    private final static String ALIGNMENT_BOTTOM_2K = "ro.rokid.2kalignment.bottom";

    private final static String HARDWARE_VERSION = "ro.rokid.hardware.version";
    private final static int BASE_WIDTH = 1280;
    private final static int BASE_HEIGHT = 720;

    private final static int BASE_WIDTH_2K = 2048;
    private final static int BASE_HEIGHT_2K = 1536;

    public static Rect getWindowRect2K(final int previewWidth, final int previewHeight, final Rect windowRect) {
        if (noAlignment2K()) {
            return null;
        }
        RectF rectF = getAlignmentPercent(BASE_WIDTH_2K, BASE_HEIGHT_2K);

        float w = ((rectF.right - rectF.left) * previewWidth);
        float h = ((rectF.bottom - rectF.top) * previewHeight);

        int left = (int) ((windowRect.left * w) * 1.0f / BASE_WIDTH + rectF.left * previewWidth);
        int top = (int) ((windowRect.top * h) * 1.0f / BASE_HEIGHT + rectF.top * previewHeight);
        int right = (int) ((windowRect.right * w) * 1.0f / BASE_WIDTH + rectF.left * previewWidth);
        int bottom = (int) ((windowRect.bottom * h) * 1.0f / BASE_HEIGHT + rectF.top * previewHeight);

        return new Rect(left, top, right, bottom);
    }


    public static Rect getWindowRect(final int previewWidth, final int previewHeight, final Rect windowRect) {
        if (noAlignment()) {
            return null;
        }
        RectF rectF = getAlignmentPercent(BASE_WIDTH, BASE_HEIGHT);

        float w = ((rectF.right - rectF.left) * previewWidth);
        float h = ((rectF.bottom - rectF.top) * previewHeight);

        int left = (int) ((windowRect.left * w) * 1.0f / BASE_WIDTH + rectF.left * previewWidth);
        int top = (int) ((windowRect.top * h) * 1.0f / BASE_HEIGHT + rectF.top * previewHeight);
        int right = (int) ((windowRect.right * w) * 1.0f / BASE_WIDTH + rectF.left * previewWidth);
        int bottom = (int) ((windowRect.bottom * h) * 1.0f / BASE_HEIGHT + rectF.top * previewHeight);

        return new Rect(left, top, right, bottom);
    }

    /**
     * 根据preview的rect，获取到映射到LCD屏幕的区域(2K)
     *
     * @param previewWidth
     * @param previewHeight
     * @param previewRect
     * @return
     */
    public static Rect getAlignmentRect2K(final int previewWidth, final int previewHeight, final Rect previewRect) {
        if (noAlignment2K()) {
            return null;
        }

        RectF rectF = getAlignmentPercent(BASE_WIDTH_2K, BASE_HEIGHT_2K);


        float w = ((rectF.right - rectF.left) * previewWidth);
        float h = ((rectF.bottom - rectF.top) * previewHeight);

        int left = (int) ((previewRect.left - rectF.left * previewWidth) * 1.0f / w * BASE_WIDTH);
        int top = (int) ((previewRect.top - rectF.top * previewHeight) * 1.0f / h * BASE_HEIGHT);
        int right = (int) ((previewRect.right - rectF.left * previewWidth) * 1.0f / w * BASE_WIDTH);
        int bottom = (int) ((previewRect.bottom - rectF.top * previewHeight) * 1.0f / h * BASE_HEIGHT);

        return new Rect(left, top, right, bottom);
    }

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

        RectF rectF = getAlignmentPercent(BASE_WIDTH, BASE_HEIGHT);


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
    public static RectF getAlignmentPercent(final int width, final int height) {
        Rect rect;
        if (width == BASE_WIDTH_2K) {
            rect = getAlignmentBaseRect2K();
        } else {
            rect = getAlignmentBaseRect();
        }
        return new RectF(rect.left * 1.0f / width, rect.top * 1.0f / height,
                rect.right * 1.0f / width, rect.bottom * 1.0f / height);
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
     * 获取不同 glass 下的 alignment 参数
     *
     * @return
     */
    private static Rect getAlignmentBaseRect2K() {
        return new Rect(toInt(getSystemProperty(ALIGNMENT_LEFT_2K)),
                toInt(getSystemProperty(ALIGNMENT_TOP_2K)),
                toInt(getSystemProperty(ALIGNMENT_RIGHT_2K)),
                toInt(getSystemProperty(ALIGNMENT_BOTTOM_2K)));

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

    private static boolean noAlignment2K() {
        return TextUtils.isEmpty(getSystemProperty(ALIGNMENT_LEFT_2K));
    }
}
