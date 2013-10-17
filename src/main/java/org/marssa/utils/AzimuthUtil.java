package org.marssa.utils;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 *
 * Native system format is signed degree angle with fraction
 * W = negative angle
 * E = positive angle
 */

public final class AzimuthUtil {
    public static final String EAST_DIRECTION = "E";
    public static final String WEST_DIRECTION = "W";

    private AzimuthUtil() {
    }

    public static final double fromDegrees(double ddmm, String direction) {
        switch(direction) {
            case WEST_DIRECTION: return -ddmm;
            case EAST_DIRECTION: return ddmm;
            case "": return ddmm;
            default: throw new IllegalArgumentException("invalid direction code " + direction);
        }
    }
}
