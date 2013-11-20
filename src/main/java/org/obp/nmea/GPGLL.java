package org.obp.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-5
 */
public class GPGLL extends NmeaMessage {
    public static final GPGLL DUMMY = new GPGLL(Double.NaN,Double.NaN,0);
    public static final String SIGNATURE = "GPGLL";

    private long fixTime;
    private double latitude;
    private double longitude;

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

    public String toString() {
        return latitude+" "+longitude;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
