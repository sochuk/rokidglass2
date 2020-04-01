package com.rokid.glass.ui.util;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @date 2019-08-08
 */

public class Alignment {
    public static final int align720p = 0;
    public static final int alignHD = 1;
    public static final int align2K = 2;

    @Retention(RetentionPolicy.SOURCE)
    @interface type {

    }
}
