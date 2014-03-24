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

import org.obp.Attributes;
import org.obp.Reliability;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public final class GpsUtil {
    private GpsUtil() {
    }

    public static Reliability estimateReliability(Attributes am) {
        // TODO: make this one of quality factors
        //GpsFixQuality fixQuality = GpsFixQuality.fromString(am.getString(GPS_FIX_QUALITY));

        if(am.containsKey(GPS_EFFECTIVE_SATELLITES)) {
            byte effectiveSatellites = am.getByte(GPS_EFFECTIVE_SATELLITES);

            if(effectiveSatellites <3) {
                return Reliability.UNDEFINED;
            } else if(effectiveSatellites <4) {
                return Reliability.AVERAGE;
            } else if(effectiveSatellites <5) {
                return Reliability.GOOD;
            } else {
                return Reliability.HIGH;
            }
        } else {
            return Reliability.UNDEFINED;
        }
    }
}
