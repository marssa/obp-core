package org.obp.utils;

import java.text.DecimalFormat;

/**
 * Created by Robert Jaremczak
 * Date: 2014-3-19
 */
public class DistanceUtil {

    public static final String UNIT = "m";
    public static final String SHORT_FORMAT = "0.00";

    private DistanceUtil() {
    }

    public static final String format(double distance) {
        return new DecimalFormat(SHORT_FORMAT).format(distance)+" "+UNIT;
    }
}
