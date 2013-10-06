package org.marssa.utils;

import static org.marssa.utils.AngleUtil.ddmmToDegrees;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-6
 *
 * Native system format is signed degree angle with fraction
 * W = negative angle
 * E = positive angle
 */

public final class LongitudeUtil {
    public static final String EAST_DIRECTION = "E";
    public static final String WEST_DIRECTION = "W";

    public static final double parseDDMM(double ddmm, String direction) {
        switch(direction) {
            case WEST_DIRECTION: return ddmmToDegrees(-ddmm);
            case EAST_DIRECTION: return ddmmToDegrees(ddmm);
            default: throw new IllegalArgumentException("invalid direction code " + direction);
        }
    }
}
