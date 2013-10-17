package org.marssa.utils;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public final class AngleUtils {

    private AngleUtils() {
    }

    public static final double fromDDMM(double ddmm) {
        int degrees = (int)(ddmm / 100);
        return degrees + (ddmm - (degrees*100))/60;
    }
}
