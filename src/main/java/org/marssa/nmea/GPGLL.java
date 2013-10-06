package org.marssa.nmea;

import java.util.regex.Pattern;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
public class GPGLL {
    private double latitude;
    private double longitude;
    private long fixTime;

    protected GPGLL(double latitude, double longitude, long fixTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.fixTime = fixTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getFixTime() {
        return fixTime;
    }
}
