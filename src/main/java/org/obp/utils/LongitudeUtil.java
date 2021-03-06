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
 * W = negative angle
 * E = positive angle
 */

public final class LongitudeUtil {
    private LongitudeUtil() {
    }

    public static final double fromDDMM(double ddmm, String direction) {
        switch(direction) {
            case AzimuthUtil.WEST_DIRECTION: return AngleUtil.fromDDMM(-ddmm);
            case AzimuthUtil.EAST_DIRECTION: return AngleUtil.fromDDMM(ddmm);
            default: throw new IllegalArgumentException("invalid direction code " + direction);
        }
    }

    public static final String toStringShort(double longitude) {
        return new DecimalFormat(AngleUtil.SHORT_ANGLE_FORMAT).format(longitude)+" "+direction(longitude);
    }

    private static String direction(double longitude) {
        return longitude >= 0 ? AzimuthUtil.EAST_DIRECTION : AzimuthUtil.WEST_DIRECTION;
    }
}
