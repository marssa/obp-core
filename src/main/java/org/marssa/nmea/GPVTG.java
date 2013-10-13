package org.marssa.nmea;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-11
 */
public class GPVTG {
    public static final GPVTG DUMMY = new GPVTG(Double.NaN,Double.NaN);
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
}
