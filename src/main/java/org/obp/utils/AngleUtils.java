package org.obp.utils;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public final class AngleUtils {

    public static final String SHORT_ANGLE_FORMAT = "0.00";

    private AngleUtils() {
    }

    public static final double fromDDMM(double ddmm) {
        int degrees = (int)(ddmm / 100);
        return degrees + (ddmm - (degrees*100))/60;
    }
}
