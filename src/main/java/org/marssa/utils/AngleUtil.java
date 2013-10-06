package org.marssa.utils;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public final class AngleUtil {
    public static final double ddmmToDegrees(double ddmm) {
        int degrees = (int)(ddmm / 100);
        return degrees + (ddmm - (degrees*100))/60;
    }
}
