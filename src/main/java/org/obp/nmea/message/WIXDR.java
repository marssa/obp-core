package org.obp.nmea.message;

import org.obp.nmea.NmeaMessage;

/**
 * Created by Robert Jaremczak
 * Date: 2013-11-26
 *
 * applies to message returned by LCJ wind-vane device
 */
public class WIXDR extends NmeaMessage {
    public static final WIXDR DUMMY = new WIXDR(Double.NaN);
    public static final String SIGNATURE = "WIXDR";

    private double windTemperature;

    public WIXDR(double windTemperature) {
        this.windTemperature = windTemperature;
    }

    @Override
    public String getSignature() {
        return null;
    }

    public double getWindTemperature() {
        return windTemperature;
    }
}
