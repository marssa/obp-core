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

    public static final String toStringShort(double latitude) {
        return new DecimalFormat(AngleUtil.SHORT_ANGLE_FORMAT).format(latitude)+" "+direction(latitude);
    }

    private static String direction(double latitude) {
        return latitude >= 0 ? NORTH_DIRECTION : SOUTH_DIRECTION;
    }
}
