package org.obp.gps;

import org.obp.nmea.message.GPGGA;
import org.obp.nmea.message.GPGSA;

import java.util.List;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-4
 */

public interface GpsReceiver {
    public long getFixTime();
    public double getLatitude();
    public double getLongitude();
    public double getTrueNorthCourse();
    public double getVelocityOverGround();
    public List<GpsSatellite> getSatellitesInView();
    public GPGGA.FixQuality getFixQuality();
    public GPGSA.FixMode getFixMode();
    public GPGSA.FixType getFixType();
    public byte getNumberOfSatellitesInView();
    public double getPDop();
    public double getHDop();
    public double getVDop();
    public double getAltitude();
}
