package org.marssa.utils;

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */
public final class VelocityUtils {
    public static final double KNOT_MPS = 0.514444444; // m/s
    public static final double KMPH_MPS = 0.277777778; // m/s

    public static final String SHORT_SPEED = "0.00";

    private VelocityUtils() {
    }

    public static final double fromKnots(double knots) {
        return KNOT_MPS * knots;
    }

    public static final double fromKmPerHour(double kmph) {
        return KMPH_MPS * kmph;
    }

    public static final double toKmph(double v) {
        return v / KMPH_MPS;
    }

    public static final double toKnots(double v) {
        return v / KNOT_MPS;
    }

    public static final String toStringKnots(double v) {
        return new DecimalFormat(SHORT_SPEED).format(v);
    }
}
