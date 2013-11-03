package org.marssa.utils;

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 *
 * Native system format is signed degree angle with fraction
 * S = negative angle
 * N = positive angle
 */

public final class LatitudeUtils {
    public static final String NORTH_DIRECTION = "N";
    public static final String SOUTH_DIRECTION = "S";

    private LatitudeUtils() {
    }

    public static final double fromDDMM(double ddmm, String direction) {
        switch(direction) {
            case SOUTH_DIRECTION: return AngleUtils.fromDDMM(-ddmm);
            case NORTH_DIRECTION: return AngleUtils.fromDDMM(ddmm);
            default: throw new IllegalArgumentException("invalid direction code " + direction);
        }
    }

    public static final String toStringShort(double latitude) {
        return new DecimalFormat(AngleUtils.SHORT_ANGLE_FORMAT).format(latitude)+" "+direction(latitude);
    }

    private static String direction(double latitude) {
        return latitude >= 0 ? NORTH_DIRECTION : SOUTH_DIRECTION;
    }
}
