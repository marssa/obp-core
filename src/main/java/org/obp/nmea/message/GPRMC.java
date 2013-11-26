package org.obp.nmea.message;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.obp.nmea.NmeaMessage;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
public class GPRMC extends NmeaMessage {
    public static final String SIGNATURE = "GPRMC";

    private static final DateTimeFormatter FIX_DATETIME_FORMATTER = DateTimeFormat.forPattern("ddMMyy HHmmss.SSS").withZoneUTC();

    private long fixTime;
    private boolean navigationReceiverWarning;
    private double latitude;
    private double longitude;
    private double trueNorthCourse;
    private double velocityOverGround;
    private double magneticVariation;

    public GPRMC(String fixTime, boolean navigationReceiverWarning, double latitude, double longitude,
                 double velocityOverGround, double trueNorthCourse, String fixDate, double magneticVariation) {
        this.fixTime = FIX_DATETIME_FORMATTER.parseMillis(fixDate+" "+fixTime);
        this.navigationReceiverWarning = navigationReceiverWarning;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trueNorthCourse = trueNorthCourse;
        this.velocityOverGround = velocityOverGround;
        this.magneticVariation = magneticVariation;
    }

    public long getFixTime() {
        return fixTime;
    }

    public boolean isNavigationReceiverWarning() {
        return navigationReceiverWarning;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTrueNorthCourse() {
        return trueNorthCourse;
    }

    public double getVelocityOverGround() {
        return velocityOverGround;
    }

    public double getMagneticVariation() {
        return magneticVariation;
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
