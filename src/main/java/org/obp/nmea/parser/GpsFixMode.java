package org.obp.nmea.parser;

import org.apache.log4j.Logger;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public enum GpsFixMode {
    NA(""), MANUAL("M"), AUTO("A");

    private static final Logger logger = Logger.getLogger(GpsFixMode.class);

    private String code;

    GpsFixMode(String code) {
        this.code = code;
    }

    public static GpsFixMode fromString(String str) {
        for(GpsFixMode m : GpsFixMode.values()) {
            if(m.code.equals(str)) {
                return m;
            }
        }
        return NA;
    }
}
