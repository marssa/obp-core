package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */
public class GPVTG extends NmeaMessage {
    public static final GPVTG DUMMY = new GPVTG(Double.NaN,Double.NaN);
    public static final String SIGNATURE = "GPVTG";

    private double trueNorthCourse;
    private double velocityOverGround;

    public GPVTG(double trueNorthCourse, double velocityOverGround) {
        this.trueNorthCourse = trueNorthCourse;
        this.velocityOverGround = velocityOverGround;
    }

    public double getTrueNorthCourse() {
        return trueNorthCourse;
    }

    public double getVelocityOverGround() {
        return velocityOverGround;
    }

    public String toString() {
        return trueNorthCourse +" "+velocityOverGround+" m/s";
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
