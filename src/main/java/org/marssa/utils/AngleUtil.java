package org.marssa.utils;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public final class AngleUtil {

    private AngleUtil() {
    }

    public static final double fromDDMM(double ddmm) {
        int degrees = (int)(ddmm / 100);
        return degrees + (ddmm - (degrees*100))/60;
    }
}
