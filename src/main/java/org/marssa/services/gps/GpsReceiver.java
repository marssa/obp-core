package org.marssa.services.gps;

import org.marssa.nmea.GPGGA;
import org.springframework.stereotype.Service;

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
    public byte getNumberOfSatellitesInView();
    public double getHdop();
    public double getAltitude();
}
