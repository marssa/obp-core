package org.obp.utils;

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 */
public final class AngleUtil {

    public static final String UNIT = "°";
    public static final String SHORT_ANGLE_FORMAT = "0.00";

    private AngleUtil() {
    }

    public static final double fromDDMM(double ddmm) {
        int degrees = (int)(ddmm / 100);
        return degrees + (ddmm - (degrees*100))/60;
    }

    public static final String format(double angle) {
        return new DecimalFormat(SHORT_ANGLE_FORMAT).format(angle)+" "+UNIT;
    }
}
