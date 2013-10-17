package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */
public class GPVTG extends NmeaMessage {
    public static final GPVTG DUMMY = new GPVTG(Double.NaN,Double.NaN);
    public static final String SIGNATURE = "GPVTG";

    private double trueNorthHeading;
    private double velocityOverGround;

    public GPVTG(double trueNorthHeading, double velocityOverGround) {
        this.trueNorthHeading = trueNorthHeading;
        this.velocityOverGround = velocityOverGround;
    }

    public double getTrueNorthHeading() {
        return trueNorthHeading;
    }

    public double getVelocityOverGround() {
        return velocityOverGround;
    }

    public String toString() {
        return trueNorthHeading+" "+velocityOverGround+" m/s";
    }

    @Override
    public String getSignature() {
        return SIGNATURE;
    }
}
