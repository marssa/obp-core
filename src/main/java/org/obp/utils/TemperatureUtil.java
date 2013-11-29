package org.obp.utils;

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public final class TemperatureUtil {
    public static final String FORMAT_SHORT = "0.0";
    public static final String UNIT = "°C";

    private TemperatureUtil() {
    }

    public static Object format(double t) {
        return new DecimalFormat(FORMAT_SHORT).format(t)+" "+UNIT;
    }
}
