package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
public class GPRMC extends NmeaMessage {

    public static final String SIGNATURE = "GPRMC";

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
