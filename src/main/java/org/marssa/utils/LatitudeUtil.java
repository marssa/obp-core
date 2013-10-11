package org.marssa.utils;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 *
 * Native system format is signed degree angle with fraction
 * S = negative angle
 * N = positive angle
 */

public final class LatitudeUtil {
    public static final String NORTH_DIRECTION = "N";
    public static final String SOUTH_DIRECTION = "S";

    private LatitudeUtil() {
    }

    public static final double fromDDMM(double ddmm, String direction) {
        switch(direction) {
            case SOUTH_DIRECTION: return AngleUtil.fromDDMM(-ddmm);
            case NORTH_DIRECTION: return AngleUtil.fromDDMM(ddmm);
            default: throw new IllegalArgumentException("invalid direction code " + direction);
        }
    }
}
