package org.obp.nmea.parser;

import org.apache.log4j.Logger;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public enum GpsFixQuality {

    NA(""),
    INVALID("0"),
    GPSFIX("1"),
    DGPSFIX("2"),
    PPSFIX("3"),
    RTK_INT("4"),
    RTK_FLOAT("5"),
    ESTIMATED("6");

    private static Logger logger = Logger.getLogger(GpsFixQuality.class);

    private String code;

    GpsFixQuality(String code) {
        this.code=code;
    }

    public static GpsFixQuality fromString(String str) {
        for(GpsFixQuality fq : GpsFixQuality.values()) {
            if(fq.code.equals(str)) {
                return fq;
            }
        }
        logger.error("undefined code "+str);
        return NA;
    }
}