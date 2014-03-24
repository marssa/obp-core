/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
