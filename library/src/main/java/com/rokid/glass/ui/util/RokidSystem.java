package com.rokid.glass.ui.util;

import android.graphics.Rect;
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
