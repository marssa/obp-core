package org.obp.utils;

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */
public final class SpeedUtil {
    public static final double MPS_PER_KNOT = 0.514444444;
    public static final double MPS_PER_KMPH = 0.277777778;

    public static final String UNIT = "m/s";
    public static final String UNIT_KNOTS = "kts";
    public static final String UNIT_KMPH = "km/h";
    public static final String FORMAT_SHORT = "0.00";

    private SpeedUtil() {
    }

    public static final double fromKnots(double knots) {
        return MPS_PER_KNOT * knots;
    }

    public static final double fromKmph(double kmph) {
        return MPS_PER_KMPH * kmph;
    }

    public static final double toKmph(double v) {
        return v / MPS_PER_KMPH;
    }

    public static final double toKnots(double v) {
        return v / MPS_PER_KNOT;
    }

    public static final String format(double v) {
        return new DecimalFormat(FORMAT_SHORT).format(v)+" "+UNIT;
    }

    public static final String formatKnots(double v) {
        return new DecimalFormat(FORMAT_SHORT).format(toKnots(v))+" "+UNIT_KNOTS;
    }
}
