package org.obp.utils;

import org.obp.Attributes;
import org.obp.Reliability;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public final class GpsUtils {
    private GpsUtils() {
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
