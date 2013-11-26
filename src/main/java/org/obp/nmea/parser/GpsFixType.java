package org.obp.nmea.parser;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 */
public enum GpsFixType {
    NA(""), NOFIX("1"), FIX2D("2"), FIX3D("3");

    private String code;

    GpsFixType(String code) {
        this.code = code;
    }

    public static GpsFixType fromString(String str) {
        for(GpsFixType m : GpsFixType.values()) {
            if(m.code.equals(str)) {
                return m;
            }
        }
        return NA;
    }
}
