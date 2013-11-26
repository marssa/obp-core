package org.obp.utils;

import org.obp.AttributeMap;
import org.obp.AttributeNames;
import org.obp.Instrument;
import org.obp.Reliability;
import org.obp.nmea.parser.GpsFixQuality;

import java.util.Map;

import static org.obp.AttributeNames.*;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public final class GpsUtils {
    private GpsUtils() {
    }

    public static Reliability estimateReliability(AttributeMap am) {
        // TODO: make this one of quality factors
        GpsFixQuality fixQuality = GpsFixQuality.fromString(am.getString(GPS_FIX_QUALITY));
        byte effectiveSatellites = am.getByte(GPS_EFFECTIVE_SATELLITES);

        if(effectiveSatellites <3) {
            return Reliability.NONE;
        } else if(effectiveSatellites <4) {
            return Reliability.MEDIUM;
        } else if(effectiveSatellites <5) {
            return Reliability.GOOD;
        } else {
            return Reliability.HIGH;
        }
    }
}
